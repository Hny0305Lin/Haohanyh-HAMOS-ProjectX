/*
 * Copyright (c) 2020, HiHope Community.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <unistd.h>

#include "ohos_init.h"
#include "cmsis_os2.h"
#include "wifiiot_i2c.h"
#include "wifiiot_gpio.h"
#include "wifiiot_gpio_ex.h"
#include "wifiiot_pwm.h"
#include "wifiiot_adc.h"
#include "wifiiot_errno.h"

#include "aht20.h"
#include "oled_ssd1306.h"

#ifndef ARRAY_SIZE
#define ARRAY_SIZE(a) sizeof(a)/sizeof(a[0])
#endif

#define MS_PER_S 1000

#define BEEP_TIMES 3
#define BEEP_DURATION 100
#define BEEP_PWM_DUTY 30000
#define BEEP_PWM_FREQ 60000
#define BEEP_PIN_NAME WIFI_IOT_IO_NAME_GPIO_9
#define BEEP_PIN_FUNCTION WIFI_IOT_IO_FUNC_GPIO_9_PWM0_OUT

#define GAS_SENSOR_CHAN_NAME WIFI_IOT_ADC_CHANNEL_5
// #define GAS_SENSOR_PIN_NAME WIFI_IOT_IO_NAME_GPIO_11

#define AHT20_BAUDRATE 400*1000
#define AHT20_I2C_IDX WIFI_IOT_I2C_IDX_0

#define ADC_RESOLUTION 2048

static float ConvertToVoltage(unsigned short data)
{
    return (float)data * 1.8 * 4 / 4096;
}

static void EnvironmentTask(void *arg)
{
    (void)arg;
    uint32_t retval = 0;
    float humidity = 0.0f;
    float temperature = 0.0f;
    float gasSensorResistance = 0.0f;
    static char line[32] = {0};

    OledInit();
    OledFillScreen(0);
    I2cInit(AHT20_I2C_IDX, AHT20_BAUDRATE);

    // set BEEP pin as PWM function
    IoSetFunc(BEEP_PIN_NAME, BEEP_PIN_FUNCTION);
    GpioSetDir(BEEP_PIN_NAME, WIFI_IOT_GPIO_DIR_OUT);
    PwmInit(WIFI_IOT_PWM_PORT_PWM0);

    for (int i = 0; i < BEEP_TIMES; i++) {
        snprintf(line, sizeof(line), "beep %d/%d", (i+1), BEEP_TIMES);
        OledShowString(0, 0, line, 1);

        PwmStart(WIFI_IOT_PWM_PORT_PWM0, BEEP_PWM_DUTY, BEEP_PWM_FREQ);
        usleep(BEEP_DURATION * 1000);
        PwmStop(WIFI_IOT_PWM_PORT_PWM0);
        usleep((1000 - BEEP_DURATION) * 1000);
    }

    while (WIFI_IOT_SUCCESS != AHT20_Calibrate()) {
        printf("AHT20 sensor init failed!\r\n");
        usleep(1000);
    }

    while(1) {
        retval = AHT20_StartMeasure();
        if (retval != WIFI_IOT_SUCCESS) {
            printf("trigger measure failed!\r\n");
        }

        retval = AHT20_GetMeasureResult(&temperature, &humidity);
        if (retval != WIFI_IOT_SUCCESS) {
            printf("get humidity data failed!\r\n");
        }

        unsigned short data = 0;
        if (AdcRead(GAS_SENSOR_CHAN_NAME, &data, WIFI_IOT_ADC_EQU_MODEL_4, WIFI_IOT_ADC_CUR_BAIS_DEFAULT, 0)
                == WIFI_IOT_SUCCESS) {
            float Vx = ConvertToVoltage(data);

            // Vcc            ADC            GND
            //  |    ______   |     ______   |
            //  +---| MG-2 |---+---| 1kom |---+
            //       ------         ------
            // 查阅原理图，ADC 引脚位于 1K 电阻和燃气传感器之间，燃气传感器另一端接在 5V 电源正极上
            // 串联电路电压和阻止成正比：
            // Vx / 5 == 1kom / (1kom + Rx)
            //   => Rx + 1 == 5/Vx
            //   =>  Rx = 5/Vx - 1
            gasSensorResistance = 5 / Vx - 1;
        }

        OledShowString(0, 0, "Sensor values:", 1);

        snprintf(line, sizeof(line), "temp: %.2f", temperature);
        OledShowString(0, 1, line, 1);

        snprintf(line, sizeof(line), "humi: %.2f", humidity);
        OledShowString(0, 2, line, 1);

        snprintf(line, sizeof(line), "gas: %.2f kom", gasSensorResistance);
        OledShowString(0, 3, line, 1);

        sleep(1);
    }
}

static void EnvironmentDemo(void)
{
    osThreadAttr_t attr;

    GpioInit();

    IoSetFunc(BEEP_PIN_NAME, BEEP_PIN_FUNCTION);
    GpioSetDir(BEEP_PIN_NAME, WIFI_IOT_GPIO_DIR_OUT);
    PwmInit(WIFI_IOT_PWM_PORT_PWM0);

    attr.name = "EnvironmentTask";
    attr.attr_bits = 0U;
    attr.cb_mem = NULL;
    attr.cb_size = 0U;
    attr.stack_mem = NULL;
    attr.stack_size = 4096;
    attr.priority = osPriorityNormal;

    if (osThreadNew(EnvironmentTask, NULL, &attr) == NULL) {
        printf("[EnvironmentDemo] Falied to create EnvironmentTask!\n");
    }
}

APP_FEATURE_INIT(EnvironmentDemo);