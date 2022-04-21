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
#include <unistd.h>

#include "ohos_init.h"
#include "cmsis_os2.h"
#include "wifiiot_gpio.h"
#include "wifiiot_gpio_ex.h"
#include "wifiiot_pwm.h"
#include "wifiiot_adc.h"
#include "wifiiot_errno.h"

#define HUMAN_SENSOR_CHAN_NAME WIFI_IOT_ADC_CHANNEL_3
#define LIGHT_SENSOR_CHAN_NAME WIFI_IOT_ADC_CHANNEL_4
// #define HUMAN_SENSOR_PIN_NAME WIFI_IOT_IO_NAME_GPIO_7
// #define LIGHT_SENSOR_PIN_NAME WIFI_IOT_IO_NAME_GPIO_9

#define RED_LED_PIN_NAME WIFI_IOT_IO_NAME_GPIO_10
#define RED_LED_PIN_FUNCTION WIFI_IOT_IO_FUNC_GPIO_10_GPIO

#define GREEN_LED_PIN_NAME WIFI_IOT_IO_NAME_GPIO_11
#define GREEN_LED_PIN_FUNCTION WIFI_IOT_IO_FUNC_GPIO_11_GPIO

#define BLUE_LED_PIN_NAME WIFI_IOT_IO_NAME_GPIO_12
#define BLUE_LED_PIN_FUNCTION WIFI_IOT_IO_FUNC_GPIO_12_GPIO

#define LED_DELAY_TIME_US 300000
#define LED_BRIGHT WIFI_IOT_GPIO_VALUE1
#define LED_DARK WIFI_IOT_GPIO_VALUE0

#define NUM_LEDS 3
#define NUM_BLINKS 2
#define NUM_SENSORS 2

#define ADC_RESOLUTION 4096
#define PWM_FREQ_DIVITION 64000

static void CorlorfulLightTask(void *arg)
{
    (void)arg;
    static const WifiIotGpioIdx pins[] = {RED_LED_PIN_NAME, GREEN_LED_PIN_NAME, BLUE_LED_PIN_NAME};

    for (int i = 0; i < NUM_BLINKS; i++) {
        for (unsigned j = 0; j < sizeof(pins)/sizeof(pins[0]); j++) {
            GpioSetOutputVal(pins[j], LED_BRIGHT);
            usleep(LED_DELAY_TIME_US);
            GpioSetOutputVal(pins[j], LED_DARK);
            usleep(LED_DELAY_TIME_US);
        }
    }

    // GpioDeinit(); 
    IoSetFunc(RED_LED_PIN_NAME, WIFI_IOT_IO_FUNC_GPIO_10_PWM1_OUT);
    IoSetFunc(GREEN_LED_PIN_NAME, WIFI_IOT_IO_FUNC_GPIO_11_PWM2_OUT);
    IoSetFunc(BLUE_LED_PIN_NAME, WIFI_IOT_IO_FUNC_GPIO_12_PWM3_OUT);

    PwmInit(WIFI_IOT_PWM_PORT_PWM1); // R
    PwmInit(WIFI_IOT_PWM_PORT_PWM2); // G
    PwmInit(WIFI_IOT_PWM_PORT_PWM3); // B

    // use PWM control BLUE LED brightness
    for (int i = 1; i <= ADC_RESOLUTION; i *= 2) {
        PwmStart(WIFI_IOT_PWM_PORT_PWM3, i, PWM_FREQ_DIVITION);
        usleep(250000);
        PwmStop(WIFI_IOT_PWM_PORT_PWM3);
    }

    while (1) {
        unsigned short duty[NUM_SENSORS] = {0, 0};
        unsigned short data[NUM_SENSORS] = {0, 0};
        static const WifiIotAdcChannelIndex chan[] = {HUMAN_SENSOR_CHAN_NAME, LIGHT_SENSOR_CHAN_NAME};
        static const WifiIotPwmPort port[] = {WIFI_IOT_PWM_PORT_PWM1, WIFI_IOT_PWM_PORT_PWM2};

        for (size_t i = 0; i < sizeof(chan)/sizeof(chan[0]); i++) { 
            if (AdcRead(chan[i], &data[i], WIFI_IOT_ADC_EQU_MODEL_4, WIFI_IOT_ADC_CUR_BAIS_DEFAULT, 0)
                == WIFI_IOT_SUCCESS) {
                duty[i] = PWM_FREQ_DIVITION * (unsigned int)data[i] / ADC_RESOLUTION;
            }
            PwmStart(port[i], duty[i], PWM_FREQ_DIVITION);
            usleep(10000);
            PwmStop(port[i]);
        }
    }
}

static void ColorfulLightDemo(void)
{
    osThreadAttr_t attr;

    GpioInit();

    // set Red/Green/Blue LED pin to GPIO function
    IoSetFunc(RED_LED_PIN_NAME, RED_LED_PIN_FUNCTION);
    IoSetFunc(GREEN_LED_PIN_NAME, GREEN_LED_PIN_FUNCTION);
    IoSetFunc(BLUE_LED_PIN_NAME, BLUE_LED_PIN_FUNCTION);

    // set Red/Green/Blue LED pin as output
    GpioSetDir(RED_LED_PIN_NAME, WIFI_IOT_GPIO_DIR_OUT);
    GpioSetDir(GREEN_LED_PIN_NAME, WIFI_IOT_GPIO_DIR_OUT);
    GpioSetDir(BLUE_LED_PIN_NAME, WIFI_IOT_GPIO_DIR_OUT);

    attr.name = "CorlorfulLightTask";
    attr.attr_bits = 0U;
    attr.cb_mem = NULL;
    attr.cb_size = 0U;
    attr.stack_mem = NULL;
    attr.stack_size = 4096;
    attr.priority = osPriorityNormal;

    if (osThreadNew(CorlorfulLightTask, NULL, &attr) == NULL) {
        printf("[ColorfulLightDemo] Falied to create CorlorfulLightTask!\n");
    }
}

APP_FEATURE_INIT(ColorfulLightDemo);
