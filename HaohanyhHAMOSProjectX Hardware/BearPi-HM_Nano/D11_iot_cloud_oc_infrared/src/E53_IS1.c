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
#include <unistd.h>
#include <math.h>
#include "cmsis_os2.h"
#include "E53_IS1.h"
#include "wifiiot_errno.h"
#include "wifiiot_gpio.h"
#include "wifiiot_gpio_ex.h"
#include "wifiiot_pwm.h"



/***************************************************************
* 函数名称: E53_SC2_IO_Init
* 说    明: E53_SC2_GPIO初始化
* 参    数: 无
* 返 回 值: 无
***************************************************************/
static void E53_IS1_IO_Init(void)
{
    GpioInit();
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_8, WIFI_IOT_IO_FUNC_GPIO_8_PWM1_OUT);//设置GPIO_8引脚复用功能为PWM
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_8, WIFI_IOT_GPIO_DIR_OUT);//设置GPIO_8引脚为输出模式
    PwmInit(WIFI_IOT_PWM_PORT_PWM1);//初始化PWM5端口
    /*****初始化F1按键，设置为下降沿触发中断*****/
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_IO_FUNC_GPIO_7_GPIO);
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_GPIO_DIR_IN);
    IoSetPull(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_IO_PULL_UP);
    
}

/***************************************************************
* 函数名称: E53_IS1_Init
* 说    明: 初始化E53_IS1
* 参    数: 无
* 返 回 值: 无
***************************************************************/
void E53_IS1_Init(void)
{
    E53_IS1_IO_Init();

}
/***************************************************************
* 函数名称: E53_IS1_Read_Data
* 说    明: 读取数据
* 参    数: 无
* 返 回 值: 无
***************************************************************/
void E53_IS1_Read_Data(E53_IS1_CallbackFunc func)
{
	GpioRegisterIsrFunc(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_INT_TYPE_EDGE, WIFI_IOT_GPIO_EDGE_RISE_LEVEL_HIGH,func, NULL);
}
/***************************************************************
* 函数名称: Beep_StatusSet
* 说    明: 蜂鸣器报警与否
* 参    数: status,ENUM枚举的数据
*									OFF,蜂鸣器
*									ON,开蜂鸣器
* 返 回 值: 无
***************************************************************/
void Beep_StatusSet(E53_IS1_Status_ENUM status)
{
	if(status == ON)
		PwmStart(WIFI_IOT_PWM_PORT_PWM1, 20000, 40000); //输出不同占空比的PWM波
	if(status == OFF)
		PwmStop(WIFI_IOT_PWM_PORT_PWM1);
}
