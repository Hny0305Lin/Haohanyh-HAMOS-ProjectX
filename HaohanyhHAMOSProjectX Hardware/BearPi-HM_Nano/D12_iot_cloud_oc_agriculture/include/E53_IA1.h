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
#ifndef __E53_IA1_H__
#define __E53_IA1_H__

/***************************************************************
* 名		称: GasStatus_ENUM
* 说    明：枚举状态结构体
***************************************************************/
typedef enum
{
	OFF = 0,
	ON
} E53_IA1_Status_ENUM;

/* E53_IA1传感器数据类型定义 ------------------------------------------------------------*/
typedef struct
{
	float    Lux;							//光照强度
	float    Humidity;        //湿度
	float    Temperature;     //温度
} E53_IA1_Data_TypeDef;



/* 寄存器宏定义 --------------------------------------------------------------------*/
#define SHT30_Addr 0x44

#define BH1750_Addr 0x23


void E53_IA1_Init(void);
void E53_IA1_Read_Data(E53_IA1_Data_TypeDef *ReadData);
void Light_StatusSet(E53_IA1_Status_ENUM status);
void Motor_StatusSet(E53_IA1_Status_ENUM status);


#endif /* __E53_IA1_H__ */

