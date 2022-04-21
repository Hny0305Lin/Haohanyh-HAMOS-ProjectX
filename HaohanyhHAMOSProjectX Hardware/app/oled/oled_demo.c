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

#include "oled_ssd1306.h"

#define ANALOG_KEY_CHAN_NAME WIFI_IOT_ADC_CHANNEL_2

static float ConvertToVoltage(unsigned short data)
{
    return (float)data * 1.8 * 4 / 4096; /* adc code equals: voltage/4/1.8*4096  */
}

static void OledTask(void *arg)
{
    (void)arg;

    GpioInit();

    OledInit();

    OledFillScreen(0x00);
    OledShowString(0, 0, "Hello, HarmonyOS", 1);
    sleep(1);

    // static const char digits[]= "0123456789";
    for (int i = 0; i < 3; i++) {
        OledFillScreen(0x00);
        for (int y = 0; y < 8; y++) {
            static const char text[] = "ABCDEFGHIJKLMNOP"; // QRSTUVWXYZ
            OledShowString(0, y, text, 1);
        }
        sleep(1);
    }

    OledFillScreen(0x00);
    while (1) {
        static char text[128] = {0};
        unsigned short data = 0;
        AdcRead(ANALOG_KEY_CHAN_NAME, &data, WIFI_IOT_ADC_EQU_MODEL_4, WIFI_IOT_ADC_CUR_BAIS_DEFAULT, 0);
        float voltage = ConvertToVoltage(data);
        snprintf(text, sizeof(text), "voltage: %.3f!", voltage);

        OledShowString(0, 1, text, 1);
        usleep(30*1000);
    }
}

static void OledDemo(void)
{
    osThreadAttr_t attr;

    attr.name = "OledTask";
    attr.attr_bits = 0U;
    attr.cb_mem = NULL;
    attr.cb_size = 0U;
    attr.stack_mem = NULL;
    attr.stack_size = 4096;
    attr.priority = osPriorityNormal;

    if (osThreadNew(OledTask, NULL, &attr) == NULL) {
        printf("[OledDemo] Falied to create OledTask!\n");
    }
}

APP_FEATURE_INIT(OledDemo);
