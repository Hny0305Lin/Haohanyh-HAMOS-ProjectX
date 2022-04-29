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

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include "cmsis_os2.h"
#include "E53_SC1.h"
#include "wifiiot_errno.h"
#include "wifiiot_gpio.h"
#include "wifiiot_gpio_ex.h"
#include "wifiiot_i2c.h"
#include "wifiiot_i2c_ex.h"


/***************************************************************
* 函数名称: E53_SC1_IO_Init
* 说    明: E53_SC1_GPIO初始化
* 参    数: 无
* 返 回 值: 无
***************************************************************/
static void E53_SC1_IO_Init(void)
{
    GpioInit();
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_IO_FUNC_GPIO_7_GPIO);//设置GPIO_2的复用功能为普通GPIO
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_7, WIFI_IOT_GPIO_DIR_OUT);//设置GPIO_2为输出模式

    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_0, WIFI_IOT_IO_FUNC_GPIO_0_I2C1_SDA);   // GPIO_0复用为I2C1_SDA
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_1, WIFI_IOT_IO_FUNC_GPIO_1_I2C1_SCL);   // GPIO_1复用为I2C1_SCL
    I2cInit(WIFI_IOT_I2C_IDX_1, 400000); /* baudrate: 400kbps */
    I2cSetBaudrate(WIFI_IOT_I2C_IDX_1, 400000);
    
}
/***************************************************************
* 函数名称: Init_BH1750
* 说    明: 写命令初始化BH1750
* 参    数: 无
* 返 回 值: 无
***************************************************************/
void Init_BH1750(void)
{
    WifiIotI2cData bh1750_i2c_data = { 0 };
    uint8_t send_data[1] = { 0x01 };
    bh1750_i2c_data.sendBuf = send_data;
    bh1750_i2c_data.sendLen = 1;
	I2cWrite(WIFI_IOT_I2C_IDX_1,(BH1750_Addr<<1)|0x00,&bh1750_i2c_data); 
}

/***************************************************************
* 函数名称: Start_BH1750
* 说    明: 启动BH1750
* 参    数: 无
* 返 回 值: 无
***************************************************************/
void Start_BH1750(void)
{
    WifiIotI2cData bh1750_i2c_data = { 0 };
    uint8_t send_data[1] = { 0x10 };
    bh1750_i2c_data.sendBuf = send_data;
    bh1750_i2c_data.sendLen = 1;
	I2cWrite(WIFI_IOT_I2C_IDX_1,(BH1750_Addr<<1)|0x00,&bh1750_i2c_data); 
}
/***************************************************************
* 函数名称: E53_SC1_Init
* 说    明: 初始化E53_SC1
* 参    数: 无
* 返 回 值: 无
***************************************************************/
void E53_SC1_Init(void)
{
	E53_SC1_IO_Init();
	Init_BH1750();
}
/***************************************************************
* 函数名称: E53_SC1_Read_Data
* 说    明: 测量光照强度
* 参    数: 无
* 返 回 值: 光照强度
***************************************************************/
float E53_SC1_Read_Data(void)
{
	int result;
    Start_BH1750(); // 启动传感器采集数据
	usleep(180000);
    WifiIotI2cData bh1750_i2c_data = { 0 };
    uint8_t recv_data[2] = { 0 };
    bh1750_i2c_data.receiveBuf = recv_data;
    bh1750_i2c_data.receiveLen = 2;
	I2cRead(WIFI_IOT_I2C_IDX_1, (BH1750_Addr<<1)|0x01,&bh1750_i2c_data);   // 读取传感器数据
	result = (recv_data[0]<<8) + recv_data[1];  //合成数据，即光照数据	
	return (float)(result/1.2);    
}
/***************************************************************
* 函数名称: Light_StatusSet
* 说    明: 灯状态设置
* 参    数: status,ENUM枚举的数据
*									OFF,光灯
*									ON,开灯
* 返 回 值: 无
***************************************************************/
void Light_StatusSet(E53_SC1_Status_ENUM status)
{
	if(status == ON)
		GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_7, 1);//设置GPIO_2输出高电平点亮LED灯
	if(status == OFF)
		GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_7, 0);//设置GPIO_2输出高电平点亮LED灯
}

