让鸿蒙WiFi功能更容易使用
==========================

使用鸿蒙原始WiFI API接口进行编程，整个过程稍显繁琐，为此我们对鸿蒙原始WiFi API接口做了一层封装，形成了一套更简单易用的接口。



## 简化的API接口

### STA模式

```c
int ConnectToHotspot(WifiDeviceConfig* apConfig);

void DisconnectWithHotspot(int netId);
```

### AP模式

```c
int StartHotspot(const HotspotConfig* config);

void StopHotspot(void);
```



## 如何编译

本项目下有两个示例代码，源码位于`demo`目录下；

1. 将当前项目代码克隆到本地openharmony源码的顶层目录；
   * 使用命令：`git clone https://gitee.com/hihopeorg/easy_wifi.git`
2. 修改openharmony的`build\lite\product\wifiiot.json`文件：
   * 将`"//applications/sample/wifi-iot/app"`替换为`"easy_wifi:app"`
3. 执行编译命令：`python build.py wifiiot`
4. 如需编译AP模式的demo，请修改`demo`目录下的BUILD.gn文件；
   * 注释掉`"wifi_connect_demo.c"`行，放开`"wifi_hotspot_demo.c"`行；



## 鸿蒙STA模式原始API接口

使用鸿蒙原始WiFI API接口进行编程，STA模式需要使用原始STA接口以及一些DHCP客户端接口。

### STA模式

鸿蒙WiFi STA模式的API接口有：

| API                                                          | 功能说明                                |
| ------------------------------------------------------------ | --------------------------------------- |
| `WifiErrorCode EnableWifi(void);`                            | 开启STA                                 |
| `WifiErrorCode DisableWifi(void);`                           | 关闭STA                                 |
| `int IsWifiActive(void);`                                    | 查询STA是否已开启                       |
| `WifiErrorCode Scan(void);`                                  | 触发扫描                                |
| `WifiErrorCode GetScanInfoList(WifiScanInfo* result, unsigned int* size);` | 获取扫描结果                            |
| `WifiErrorCode AddDeviceConfig(const WifiDeviceConfig* config, int* result);` | 添加热点配置，成功会通过result传出netId |
| `WifiErrorCode GetDeviceConfigs(WifiDeviceConfig* result, unsigned int* size);` | 获取本机所有热点配置                    |
| `WifiErrorCode RemoveDevice(int networkId);`                 | 删除热点配置                            |
| `WifiErrorCode ConnectTo(int networkId);`                    | 连接到热点                              |
| `WifiErrorCode Disconnect(void);`                            | 断开热点连接                            |
| `WifiErrorCode GetLinkedInfo(WifiLinkedInfo* result);`       | 获取当前连接热点信息                    |
| `WifiErrorCode RegisterWifiEvent(WifiEvent* event);`         | 注册事件监听                            |
| `WifiErrorCode UnRegisterWifiEvent(const WifiEvent* event);` | 解除事件监听                            |
| `WifiErrorCode GetDeviceMacAddress(unsigned char* result);`  | 获取Mac地址                             |
| `WifiErrorCode AdvanceScan(WifiScanParams *params);`         | 高级搜索                                |



#### DHCP 客户端接口

以及DHCP客户端接口：

| API                 | 描述               |
| ------------------- | ------------------ |
| netifapi_netif_find | 按名称查找网络接口 |
| netifapi_dhcp_start | 启动DHCP客户端     |
| netifapi_dhcp_stop  | 停止DHCP客户端     |



### AP模式

使用鸿蒙原始WiFI API接口进行编程，AP模式需要使用原始AP模式接口以及一些DHCP服务端接口。

鸿蒙WiFi STA模式的API接口有：

| API                                                          | 说明                 |
| ------------------------------------------------------------ | -------------------- |
| `WifiErrorCode EnableHotspot(void);`                         | 打开AP模式           |
| `WifiErrorCode DisableHotspot(void);`                        | 关闭AP模式           |
| `WifiErrorCode SetHotspotConfig(const HotspotConfig* config);` | 设置当前热点配置参数 |
| `WifiErrorCode GetHotspotConfig(HotspotConfig* result);`     | 获取当前热点配置参数 |
| `int IsHotspotActive(void);`                                 | 查询AP是否已开启     |
| `WifiErrorCode GetStationList(StationInfo* result, unsigned int* size);` | 获取接入的设备列表   |
| `int GetSignalLevel(int rssi, int band);`                    | 获取信号强度等级     |
| `WifiErrorCode SetBand(int band);`                           | 设置当前频段         |
| `WifiErrorCode GetBand(int* result);`                        | 获取当前频段         |



#### DHCP服务端接口

以及一些DHCP服务端接口：

| API                     | 描述                             |
| ----------------------- | -------------------------------- |
| netifapi_netif_set_addr | 设置当前接口的IP、网关、子网掩码 |
| netifapi_dhcps_start    | 启动DHCP服务端                   |
| netifapi_dhcps_stop     | 停止DHCP服务端                   |