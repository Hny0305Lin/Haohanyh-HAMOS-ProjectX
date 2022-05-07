中国移动Onenet

DeviceID为硬件ID，ApiKey是API密钥（需要手机号验证）

华为云IoTDA

> String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/******/devices/******/shadow");

[点我了解该填写方法和API法则](https://apiexplorer.developer.huaweicloud.com/apiexplorer/doc?product=IoTDA&api=ShowDeviceShadow)

> String jsonwenben = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"******\"},\"name\":\"******\",\"password\":\"******\"}}},\"scope\":{\"domain\":{\"name\":\"******\"}}}}";

[点我了解该填写方法和API法则](https://apiexplorer.developer.huaweicloud.com/apiexplorer/doc?product=IAM&api=KeystoneCreateAgencyToken)

华为云有点特殊，token为24小时（不确定），所以我们得通过POST载下来HUAWEITOKEN，然后再GET请求得到设备影子数据

> void Huawei(String project_id,String device_id,boolean iam)
> void Huawei(String project_id,String device_id)

第一个为自动化函数，第二个为正常函数，无非就是多了个boolean。

然后使用方法是一样的，只不过第一个函数写的很特殊。

然后华为云的设备控制，这个API是可以用的。[点我了解该填写方法和API法则](https://apiexplorer.developer.huaweicloud.com/apiexplorer/doc?product=IoTDA&api=CreateCommand)

那么，就这么水到渠成的完成了华为云+鸿蒙小熊派开发板+Android，三方的上报、收发、控制呢~！

:)
