/*
 * Copyright (c) 2020 Nanjing Xiaoxiongpai Intelligent Technology Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include "ohos_init.h"
#include "cmsis_os2.h"

#include "wifi_connect.h"
#include <queue.h>
#include <oc_mqtt_al.h>
#include <oc_mqtt_profile.h>
#include "E53_IS1.h"
#include <dtls_al.h>
#include <mqtt_al.h>


#define CONFIG_WIFI_SSID          "233333333"                            //修改为自己的WiFi 热点账号

#define CONFIG_WIFI_PWD           "233333333"                        //修改为自己的WiFi 热点密码

#define CONFIG_APP_SERVERIP       "**********.iot-mqtts.cn-north-4.myhuaweicloud.com"

#define CONFIG_APP_SERVERPORT     "1883"

#define CONFIG_APP_DEVICEID       "**********"       //替换为注册设备后生成的deviceid

#define CONFIG_APP_DEVICEPWD      "**********"                                   //替换为注册设备后生成的密钥

#define CONFIG_APP_LIFETIME         60     ///< seconds

#define CONFIG_QUEUE_TIMEOUT        (5*1000)

#define MSGQUEUE_OBJECTS 16 // number of Message Queue Objects

typedef enum
{
    en_msg_cmd = 0,
    en_msg_report,
}en_msg_type_t;

typedef struct
{
    char *request_id;
    char *payload;
} cmd_t;

typedef struct
{
    queue_t                     *app_msg;
    int                          connected;
}app_cb_t;
static app_cb_t  g_app_cb;

int Infrared_Status = 0;

static void deal_report_msg(void)
{
    oc_mqtt_profile_service_t service;
    oc_mqtt_profile_kv_t status;

    if(g_app_cb.connected != 1){
        return;
    }
    service.event_time = NULL;
    service.service_id = "Infrared";
    service.service_property = &status;
    service.nxt = NULL;
    
    status.key = "Infrared_Status";
    status.value = Infrared_Status ? "Intrude" : "Safe";
    status.type = EN_OC_MQTT_PROFILE_VALUE_STRING;
    status.nxt = NULL;

    oc_mqtt_profile_propertyreport(NULL,&service);
    return;
}

#define FLAGS_MSK1 0x00000001U

osEventFlagsId_t evt_id;

static void Beep_Alarm(char *arg)
{
    (void)arg;
    osEventFlagsSet(evt_id, FLAGS_MSK1);
}

static int task_main_entry(void)
{
    uint32_t ret ;
    WifiConnect(CONFIG_WIFI_SSID, CONFIG_WIFI_PWD);
    dtls_al_init();
    mqtt_al_init();
    oc_mqtt_init();
    
    g_app_cb.app_msg = queue_create("queue_rcvmsg",10,1);
    if(NULL ==  g_app_cb.app_msg){
        printf("Create receive msg queue failed");
        
    }
    oc_mqtt_profile_connect_t  connect_para;
    (void) memset( &connect_para, 0, sizeof(connect_para));

    connect_para.boostrap =      0;
    connect_para.device_id =     CONFIG_APP_DEVICEID;
    connect_para.device_passwd = CONFIG_APP_DEVICEPWD;
    connect_para.server_addr =   CONFIG_APP_SERVERIP;
    connect_para.server_port =   CONFIG_APP_SERVERPORT;
    connect_para.life_time =     CONFIG_APP_LIFETIME;
    connect_para.rcvfunc =       NULL;
    connect_para.security.type = EN_DTLS_AL_SECURITY_TYPE_NONE;
    ret = oc_mqtt_profile_connect(&connect_para);
    if((ret == (int)en_oc_mqtt_err_ok)){
        g_app_cb.connected = 1;
        printf("oc_mqtt_profile_connect succed!\r\n");
    }
    else
    {
        printf("oc_mqtt_profile_connect faild!\r\n");
    }
    E53_IS1_Init();
    E53_IS1_Read_Data(Beep_Alarm);
    deal_report_msg();
    while (1)
    {           
        osEventFlagsWait(evt_id, FLAGS_MSK1, osFlagsWaitAny, osWaitForever);
        Beep_StatusSet(ON);
        Infrared_Status = 1;
        deal_report_msg();
        
        osDelay(1000);

        Beep_StatusSet(OFF);
        Infrared_Status = 0;
        deal_report_msg();
    }
    return 0;
}


static void OC_Demo(void)
{
    evt_id = osEventFlagsNew(NULL);
    if (evt_id == NULL)
    {
        printf("Falied to create EventFlags!\n");
    }
    osThreadAttr_t attr;

    attr.name = "task_main_entry";
    attr.attr_bits = 0U;
    attr.cb_mem = NULL;
    attr.cb_size = 0U;
    attr.stack_mem = NULL;
    attr.stack_size = 10240;
    attr.priority = 24;

    if (osThreadNew((osThreadFunc_t)task_main_entry, NULL, &attr) == NULL)
    {
        printf("Falied to create task_main_entry!\n");
    }
}

APP_FEATURE_INIT(OC_Demo);