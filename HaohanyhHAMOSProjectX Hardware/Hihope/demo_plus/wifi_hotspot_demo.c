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
#include <string.h>
#include "ohos_init.h"
#include "cmsis_os2.h"

#include "wifi_starter.h"

static void WifiHotspotTask(void *arg)
{
    (void)arg;
    WifiErrorCode errCode;
    HotspotConfig config = {0};

    // 准备AP的配置参数
    strcpy(config.ssid, "HiSpark-AP");
    strcpy(config.preSharedKey, "12345678");
    config.securityType = WIFI_SEC_TYPE_PSK;
    config.band = HOTSPOT_BAND_TYPE_2G;
    config.channelNum = 7;

    osDelay(10);

    printf("starting AP ...\r\n");
    errCode = StartHotspot(&config);
    printf("StartHotspot: %d\r\n", errCode);

    int timeout = 60;
    while (timeout--) {
        printf("After %d seconds Ap will turn off!\r\n", timeout);
        osDelay(100);
    }

    printf("stop AP ...\r\n");
    StopHotspot();
    printf("stop AP ...\r\n");
    osDelay(10);
}

static void WifiHotspotDemo(void)
{
    osThreadAttr_t attr;

    attr.name = "WifiHotspotTask";
    attr.attr_bits = 0U;
    attr.cb_mem = NULL;
    attr.cb_size = 0U;
    attr.stack_mem = NULL;
    attr.stack_size = 10240;
    attr.priority = osPriorityNormal;

    if (osThreadNew(WifiHotspotTask, NULL, &attr) == NULL) {
        printf("[WifiHotspotDemo] Falied to create WifiHotspotTask!\n");
    }
}

SYS_RUN(WifiHotspotDemo);

