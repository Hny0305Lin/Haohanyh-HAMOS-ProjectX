/* 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE */
#include <stdio.h>

#include <unistd.h>

#include "ohos_init.h"
#include "cmsis_os2.h"
#include "wifiiot_gpio.h"
#include "wifiiot_gpio_ex.h"
#include "wifiiot_i2c.h"
#include "aht20.h"
#include <hi_adc.h>
#include <hi_stdlib.h>
#include <hi_early_debug.h>

#include <unistd.h>
#include "hi_wifi_api.h"
//#include "wifi_sta.h"
#include "lwip/ip_addr.h"
#include "lwip/netifapi.h"

#include "lwip/sockets.h"

#include "MQTTPacket.h"
#include "transport.h"

#define ADC_TEST_LENGTH  64
#define VLT_MIN 100

hi_u16 g_adc_buf[ADC_TEST_LENGTH] = { 0 };

float convert_to_voltage(hi_u32 data_len)
{
    hi_u32 i;
    float vlt_max = 0;
    float vlt_min = VLT_MIN;
	float vlt_sum = 0;

    float vlt_val = 0;

    hi_u16 vlt;
    for (i = 0; i < data_len; i++) {
        vlt = g_adc_buf[i];
        float voltage = (float)vlt * 1.8 * 4 / 4096.0;  /* vlt * 1.8 * 4 / 4096.0: Convert code into voltage */
        vlt_max = (voltage > vlt_max) ? voltage : vlt_max;
        vlt_min = (voltage < vlt_min) ? voltage : vlt_min;
		vlt_sum += voltage;
    }

    vlt_val = (vlt_sum - vlt_min - vlt_max) / (data_len - 2.0);

	return vlt_val;
}


float app_demo_adc_test(void)
{
    hi_u32 ret, i;
    hi_u16 data;  /* 10 */

    memset_s(g_adc_buf, sizeof(g_adc_buf), 0x0, sizeof(g_adc_buf));
 
    for (i = 0; i < ADC_TEST_LENGTH; i++) {
        ret = hi_adc_read((hi_adc_channel_index)HI_ADC_CHANNEL_2, &data, HI_ADC_EQU_MODEL_1, HI_ADC_CUR_BAIS_DEFAULT, 0);
        if (ret != HI_ERR_SUCCESS) {
            printf("ADC Read Fail\n");
            return -1;
        }
        g_adc_buf[i] = data;
    }
    return convert_to_voltage(ADC_TEST_LENGTH);
}

int toStop = 0;

int mqtt_connect(void)
{
	MQTTPacket_connectData data = MQTTPacket_connectData_initializer;
	int rc = 0;
	int mysock = 0;
	unsigned char buf[200];
	int buflen = sizeof(buf);
	int msgid = 1;
	MQTTString topicString = MQTTString_initializer;
	int req_qos = 0;
	char msg_buf[200];
	char dataTemplete[]="{\"temperature\":%.2f,\"humidity\":%.2f}";
	char msgJson[75];
	int len = 0;
	char *host = "mqtt.heclouds.com";
 	int port = 6002;


	mysock = transport_open(host, port);
	if(mysock < 0)
		return mysock;

	printf("Haohanyh IoT AlphaTest [MQTT]Sending to hostname %s port %d\n", host, port);

	data.clientID.cstring = "********";			// ClientID
	data.keepAliveInterval = 120;
	data.cleansession = 1;
	data.username.cstring = "******";		//Username
	data.password.cstring = "************************=";	//Password

	len = MQTTSerialize_connect(buf, buflen, &data);
	rc = transport_sendPacketBuffer(mysock, buf, len);

	/* wait for connack */
	if (MQTTPacket_read(buf, buflen, transport_getdata) == CONNACK)
	{
		unsigned char sessionPresent, connack_rc;

		if (MQTTDeserialize_connack(&sessionPresent, &connack_rc, buf, buflen) != 1 || connack_rc != 0)
		{
			printf("Haohanyh IoT AlphaTest Unable to connect, return code %d\n", connack_rc);
			//goto exit;
		}
	}
	else
		goto exit;

	/* subscribe */
	topicString.cstring = "substopic";
	len = MQTTSerialize_subscribe(buf, buflen, 0, msgid, 1, &topicString, &req_qos);
	rc = transport_sendPacketBuffer(mysock, buf, len);
	if (MQTTPacket_read(buf, buflen, transport_getdata) == SUBACK) 	/* wait for suback */
	{
		unsigned short submsgid;
		int subcount;
		int granted_qos;

		rc = MQTTDeserialize_suback(&submsgid, 1, &subcount, &granted_qos, buf, buflen);
		if (granted_qos != 0)
		{
			printf("granted qos != 0, %d\n", granted_qos);
			//goto exit;
		}
	}
	else
		goto exit;
	

	IoSetFunc(WIFI_IOT_IO_NAME_GPIO_13, WIFI_IOT_IO_FUNC_GPIO_13_I2C0_SDA);
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_14, WIFI_IOT_IO_FUNC_GPIO_14_I2C0_SCL);

    I2cInit(WIFI_IOT_I2C_IDX_0, 400*1000);

    AHT20_Calibrate();
    printf("AHT20_Calibrate\r\n");
	

	/* loop getting msgs on subscribed topic */
	topicString.cstring = "$dp";
	while (!toStop)
	{
		float temp = 0.0, humi = 0.0,adc_val = 0.0;
		/* transport_getdata() has a built-in 1 second timeout,
		your mileage will vary */
		if (MQTTPacket_read(buf, buflen, transport_getdata) == PUBLISH)
		{
			unsigned char dup;
			int qos;
			unsigned char retained;
			unsigned short msgid;
			int payloadlen_in;
			unsigned char* payload_in;
			int rc;
			MQTTString receivedTopic;
			rc = MQTTDeserialize_publish(&dup, &qos, &retained, &msgid, &receivedTopic,
					&payload_in, &payloadlen_in, buf, buflen);
			printf("message arrived %.*s\n", payloadlen_in, payload_in);
		
			if(strncmp((char*)payload_in,"0",payloadlen_in)==0)
			{
				printf("received 0\n");
				GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_9, 1);

			}
			else
			{
				printf("received 1\n");
				GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_9, 0);
			}
			

            rc = rc;
        }

		AHT20_StartMeasure();
        printf("AHT20_StartMeasure\r\n");

        AHT20_GetMeasureResult(&temp, &humi);
        printf("AHT20_GetMeasureResult, temp = %.2f, humi = %.2f\r\n", temp, humi);
		sleep(1);

		adc_val = app_demo_adc_test();
		printf("adc value=%.2f",adc_val);

		snprintf(msgJson,40,dataTemplete,temp,humi);
		unsigned short json_len=strlen(msgJson);
		msg_buf[0]=(char)(0x03);
		msg_buf[1]=(char)(json_len>>8);
		msg_buf[2]=(char)(json_len&0xff);
		memcpy(msg_buf+3,msgJson,strlen(msgJson));
		msg_buf[3+strlen(msgJson)]=0;

		printf("publishing reading\n");
		// len = MQTTSerialize_publish(buf, buflen, 0, 0, 0, 0, topicString, (unsigned char*)payload, payloadlen);
		// len = MQTTSerialize_publish((unsigned char*)dp, dplen, 0, 0, 0, 0, topicString, (unsigned char*)msg_buf, 3+strlen(msgJson));
		len = MQTTSerialize_publish(buf, buflen, 0, 0, 0, 0, topicString, (unsigned char*)msg_buf, 3+strlen(msgJson));
		
        rc = transport_sendPacketBuffer(mysock, buf, len);
	}

	printf("disconnecting\n");
	len = MQTTSerialize_disconnect(buf, buflen);
	rc = transport_sendPacketBuffer(mysock, buf, len);
exit:
	transport_close(mysock);

    rc = rc;

	return 0;
}


void mqtt_test(void)
{
	IoSetFunc(WIFI_IOT_IO_NAME_GPIO_9, WIFI_IOT_IO_FUNC_GPIO_9_GPIO);
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_9, WIFI_IOT_GPIO_DIR_OUT);
    mqtt_connect();
}

