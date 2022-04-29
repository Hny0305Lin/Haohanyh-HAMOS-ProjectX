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

#ifndef __E53_IS2_H__
#define __E53_IS2_H__

typedef void (*E53_IS1_CallbackFunc) (char *arg);

typedef enum
{
	OFF = 0,
	ON
} E53_IS1_Status_ENUM;

void E53_IS1_Init(void);
void E53_IS1_Read_Data(E53_IS1_CallbackFunc func);
void Beep_StatusSet(E53_IS1_Status_ENUM status);


#endif 
