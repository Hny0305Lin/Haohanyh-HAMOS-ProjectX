中国移动Onenet

DeviceID为硬件ID，ApiKey是API密钥（需要手机号验证）

华为云IoTDA

> String result = get("https://iotda.cn-north-4.myhuaweicloud.com/v5/iot/******/devices/******/shadow");

[点我了解该填写方法和API法则](https://apiexplorer.developer.huaweicloud.com/apiexplorer/doc?product=IoTDA&api=ShowDeviceShadow)

> String jsonwenben = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"******\"},\"name\":\"******\",\"password\":\"******\"}}},\"scope\":{\"domain\":{\"name\":\"******\"}}}}";

[点我了解该填写方法和API法则](https://apiexplorer.developer.huaweicloud.com/apiexplorer/doc?product=IAM&api=KeystoneCreateAgencyToken)

华为云有点特殊，token为24小时（不确定），所以我们得通过POST载下来HUAWEITOKEN，然后再GET请求得到设备影子数据
