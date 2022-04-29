# BearPi-HM_Nano开发板智慧安防案例开发
本示例将演示如何在BearPi-HM_Nano开发板上使用MQTT协议连接华为IoT平台，使用E53_IS1 智慧安防扩展板与 BearPi-HM_Nano 开发板实现智慧安防的案例。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/E53_IS1安装.png "E53_IS1安装")

# 华为IoT平台 API


## 数据上传

### 设备消息上报

> int oc_mqtt_profile_msgup(char *deviceid,oc_mqtt_profile_msgup_t *payload);

是指设备无法按照产品模型中定义的属性格式进行数据上报时，可调用此接口将设备的自定义数据上报给平台，平台将设备上报的消息转发给应用服务器或华为云其他云服务上进行存储和处理。

| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 要上传的消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |

### 设备上报属性数据

> int oc_mqtt_profile_propertyreport(char *deviceid,oc_mqtt_profile_service_t *payload);

用于设备按产品模型中定义的格式将属性数据上报给平台。

| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 要上传的消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |

>**属性上报和消息上报的区别，请查看[消息通信说明](https://support.huaweicloud.com/usermanual-iothub/iot_01_0045_2.html)**

### 网关批量上报属性数据

> int oc_mqtt_profile_gwpropertyreport(char *deviceid,oc_mqtt_profile_device_t *payload);

用于批量设备上报属性数据给平台。网关设备可以用此接口同时上报多个子设备的属性数据。

| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 要上传的消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |

### 属性设置的响应结果

int oc_mqtt_profile_propertysetresp(char *deviceid,oc_mqtt_profile_propertysetresp_t *payload);


| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |

### 属性查询响应结果

> int oc_mqtt_profile_propertygetresp(char *deviceid,oc_mqtt_profile_propertygetresp_t *payload);


| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |

### 将命令的执行结果返回给平台

> int oc_mqtt_profile_cmdresp(char *deviceid,oc_mqtt_profile_cmdresp_t *payload);
平台下发命令后，需要设备及时将命令的执行结果返回给平台，如果设备没回响应，平台会认为命令执行超时。

| **参数**  | **描述**    	|
| :-----   | :-----  	  |
|deviceid     | 设备id       	|
|payload       | 要上传的消息   |
| **返回**  | **描述**     |
|0         | 上传成功 	   |
|1        | 上传失败 	   |



## 软件设计



### 连接平台
在连接平台前需要设置获取CONFIG_APP_DEVICEID、CONFIG_APP_DEVICEPWD、CONFIG_APP_SERVERIP、CONFIG_APP_SERVERPORT，通过oc_mqtt_profile_connect()函数连接平台。
```c
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
    connect_para.rcvfunc =       msg_rcv_callback;
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
```

### 推送数据

当需要上传数据时，需要先拼装数据，让后通过oc_mqtt_profile_propertyreport上报数据。代码示例如下： 

```c
static void deal_report_msg(void)
{
    oc_mqtt_profile_service_t service;
    oc_mqtt_profile_kv_t status;

    service.event_time = NULL;
    service.service_id = "Infrared";
    service.service_property = &status;
    service.nxt = NULL;
    
    status.key = "Infrared_Status";
    status.value = Infrared_Status ? "Intrude" : "Safe";
    status.type = EN_OC_MQTT_PROFILE_VALUE_STRING;
    status.nxt = NULL;

    oc_mqtt_profile_propertyreport(USERNAME, &service);
    return;
}
```






## 编译调试


### 登录

设备接入华为云平台之前，需要在平台注册用户账号，华为云地址：<https://www.huaweicloud.com/>

在华为云首页单击产品，找到IoT物联网，单击设备接入IoTDA 并单击立即使用。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/登录平台01.png "登录平台")

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/登录平台02.png "登录平台")

### 创建产品

在设备接入页面可看到总览界面，展示了华为云平台接入的协议与域名信息，根据需要选取MQTT通讯必要的信息备用。

接入协议（端口号）：MQTT 1883

域名：iot-mqtts.cn-north-4.myhuaweicloud.com



![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/查看平台信息.png "查看平台信息")

选中侧边栏产品页，单击右上角“创建产品”，在页面中选中所属资源空间，并且按要求填写产品名称，选中MQTT协议，数据格式为JSON，并填写厂商名称，在下方模型定义栏中选择所属行业以及添加设备类型，并单击右下角“确定”如图：

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/创建产品01.png "创建产品")

创建完成后，在产品页会自动生成刚刚创建的产品，单击“查看”可查看创建的具体信息。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/创建产品02.png "创建产品")


单击产品详情页的自定义模型，在弹出页面中新增服务

服务ID：`Infrared`(必须一致)

服务类型：`Senser`(可自定义)
![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/创建产品03.png "创建产品")

在“Infrared”的下拉菜单下点击“添加属性”填写“Infrared_Status”相关信息。


![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/创建产品04.png "创建产品")



#### 注册设备

在侧边栏中单击“设备”，进入设备页面，单击右上角“注册设备”，勾选对应所属资源空间并选中刚刚创建的产品，注意设备认证类型选择“秘钥”，按要求填写秘钥。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/注册设备01.png "注册设备")

记录下设备ID和设备密钥
![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/注册设备02.png "注册设备")

注册完成后，在设备页面单击“所有设备”，即可看到新建的设备，同时设备处于未激活状态

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/注册设备03.png "注册设备")


### 修改代码中设备信息
修改`iot_cloud_oc_sample.c`中第31行附近的wifi的ssid和pwd，以及设备的DEVICEID和DEVICEPWD（这两个参数是在平台注册设备时产生的），

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/修改设备信息.png "修改设备信息")

### 修改 BUILD.gn 文件

修改 `applications\sample\BearPi\BearPi-HM_Nano`路径下 BUILD.gn 文件，指定 `cloud_oc_infrared` 参与编译。

```r
#"D8_iot_cloud_oc_smoke:cloud_oc_smoke"
#"D9_iot_cloud_oc_light:cloud_oc_light"
#"D10_iot_cloud_oc_manhole_cover:cloud_oc_manhole_cover"
"D11_iot_cloud_oc_infrared:cloud_oc_infrared"
#"D12_iot_cloud_oc_agriculture:cloud_oc_agriculture"
```
### 编译调试
示例代码编译烧录代码后，按下开发板的RESET按键，通过串口助手查看日志，平台上的设备显示为在线状态。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/设备在线.png "设备在线")
    
点击设备右侧的“查看”，进入设备详情页面，可看到上报的数据

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/D11_iot_cloud_oc_infrared/查看设备信息.png "查看设备信息")

