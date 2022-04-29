# BearPi-HM_Nano开发板基础外设开发——GPIO输入
本示例将演示如何在BearPi-HM_Nano开发板上使用GPIO输入功能去读取按键状态

![BearPi-HM_Nano](/applications/BearPi/BearPi-HM_Nano/docs/figures/00_public/BearPi-HM_Nano.png)
## GPIO API分析
本案例主要使用了以下几个API完成GPIO输出功能
## GpioInit()
```c
unsigned int GpioInit (void )
```
 **描述：**

初始化GPIO外设
## IoSetFunc()
```c
unsigned int IoSetFunc (WifiIotIoName id, unsigned char val )
```
**描述：**

设置GPIO引脚复用功能

**参数：**

|名字|描述|
|:--|:------| 
| id | 表示GPIO引脚号.  |
| val | 表示GPIO复用功能 |

## GpioSetDir()
```c
unsigned int GpioSetDir (WifiIotGpioIdx id, WifiIotGpioDir dir )
```
**描述：**

设置GPIO输出方向

**参数：**

|名字|描述|
|:--|:------| 
| id | 表示GPIO引脚号.  |
| dir | 表示GPIO输出方向.  |


## IoSetPull()
```c
unsigned int IoSetPull (WifiIotIoName id, WifiIotIoPull val )
```
**描述：**

设备GPIO的上下拉方式

**参数：**

|名字|描述|
|:--|:------| 
| id | 表示GPIO引脚号.  |
| val | 表示要设置的上拉或下拉.  |


## GpioRegisterIsrFunc()
```c
unsigned int GpioRegisterIsrFunc (WifiIotGpioIdx id, WifiIotGpioIntType intType, WifiIotGpioIntPolarity intPolarity, GpioIsrCallbackFunc func, char * arg )
```
**描述：**

启用GPIO引脚的中断功能。这个函数可以用来为GPIO pin设置中断类型、中断极性和中断回调。

**参数：**

|名字|描述|
|:--|:------| 
| id | 表示GPIO引脚号.  |
| intType| 表示中断类型.  |
| intPolarity| 表示中断极性.  |
| func| 表示中断回调函数.  |
| arg| 表示中断回调函数中使用的参数的指针.  |


## 硬件设计
本案例将使用板载的两个用户按键来验证GPIO的输入功能，在BearPi-HM_Nano开发板上用户按键的连接电路图如下图所示，按键F1的检测引脚与主控芯片的GPIO_11连接，按键F2的检测引脚与主控芯片的GPIO_12连接，所以需要编写软件去读取GPIO_11和GPIO_12的电平值，判断按键是否被按下。

![](/applications/BearPi/BearPi-HM_Nano/docs/figures/B2_basic_button/按键电路.png "按键电路")

## 软件设计

**主要代码分析**

这部分代码主要分析按键触发中断的功能代码，这里以按键F1为例，按键F1的检测引脚与主控芯片的GPIO_11连接，首先通过调用IoSetFunc()和GpioSetDir()将GPIO_11设置为普通GPIO的输出模式。从前面原理图可知，当按键按下时，GPIO_11会被下拉到地，所以这里要使用IoSetPull()将GPIO_11引脚设置为上拉，这样才能产生电平的跳变。最后通过GpioRegisterIsrFunc()将中断类型设置为边沿触发，且为下降沿触发，当按键被按下时，GPIO_11会从高电平转为低电平，产生一个下降，这个时候就会触发中断并回调F1_Pressed函数。在F1_Pressed函数中实现点亮LED灯操作。
```c
static void F1_Pressed(char *arg)
{
    (void) arg;
    GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_2, 1);
}
static void F2_Pressed(char *arg)
{
    (void) arg;
    GpioSetOutputVal(WIFI_IOT_IO_NAME_GPIO_2, 0);
}
static void ButtonExampleEntry(void)
{
    GpioInit();
    /*****初始化LED灯*****/
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_2, WIFI_IOT_IO_FUNC_GPIO_2_GPIO);
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_2, WIFI_IOT_GPIO_DIR_OUT);
    /*****初始化F1按键，设置为下降沿触发中断*****/
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_11, WIFI_IOT_IO_FUNC_GPIO_11_GPIO);
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_11, WIFI_IOT_GPIO_DIR_IN);
    IoSetPull(WIFI_IOT_IO_NAME_GPIO_11, WIFI_IOT_IO_PULL_UP);
    GpioRegisterIsrFunc(WIFI_IOT_IO_NAME_GPIO_11, WIFI_IOT_INT_TYPE_EDGE, WIFI_IOT_GPIO_EDGE_FALL_LEVEL_LOW,F1_Pressed, NULL);
    /*****初始化F2按键，设置为下降沿触发中断*****/
    IoSetFunc(WIFI_IOT_IO_NAME_GPIO_12, WIFI_IOT_IO_FUNC_GPIO_12_GPIO);
    GpioSetDir(WIFI_IOT_IO_NAME_GPIO_12, WIFI_IOT_GPIO_DIR_IN);
    IoSetPull(WIFI_IOT_IO_NAME_GPIO_12, WIFI_IOT_IO_PULL_UP);
    GpioRegisterIsrFunc(WIFI_IOT_IO_NAME_GPIO_12, WIFI_IOT_INT_TYPE_EDGE, WIFI_IOT_GPIO_EDGE_FALL_LEVEL_LOW,F2_Pressed, NULL);

}
```

## 编译调试

### 修改 BUILD.gn 文件


修改`applications\BearPi\BearPi-HM_Nano\sample` 路径下 BUILD.gn 文件，指定 `button_example` 参与编译。

```r
#"B1_basic_led_blink:led_example",
"B2_basic_button:button_example",
#"B3_basic_pwm_led:pwm_example",
#"B4_basic_adc:adc_example",
#"B5_basic_i2c_nfc:i2c_example",
#"B6_basic_uart:uart_example",
```   

    


### 运行结果<a name="section18115713118"></a>

示例代码编译烧录代码后，按下开发板的RESET按键，开发板开始正常工作，按下F1按键LED会点亮，按下F2按键LED会熄灭。


