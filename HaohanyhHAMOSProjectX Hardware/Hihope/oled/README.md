# 使用Harmony OS控制外设——读取传感器

**本节课程中主要介绍如何在HiSpark WiFi IoT套件上使用Hamony OS的传感器相关编程接口，即环境感知能力。**



## 相关知识点

本节涉及Harmony OS外设相关接口：

* ADC接口

  * `wifiiot_adc.h` 头文件

  * ```c
    unsigned int AdcRead(WifiIotAdcChannelIndex channel, unsigned short *data, WifiIotAdcEquModelSel equModel,
                         WifiIotAdcCurBais curBais, unsigned short rstCnt);
    ```

* I2C接口

  * `wifiiot_i2c.h`头文件
  * `I2cInit`/`I2cDeinit`/`I2cRead`/`I2cWrite`



## 外设与主控芯片引脚的对应关系

* HiSpark Wi-Fi IoT 开发套件 炫彩灯板

  * 三色LED——PWM占空比控制颜色
    * GPIO10: 红
    * GPIO11: 绿
    * GPIO12: 蓝
  * 光敏电阻——ADC采集值和电压对应，电压和电阻对应，电阻和光照强度对应
    * GPIO09: ADC4
  * 人体红外传感器——ADC采集值反应是否有人靠近
    * GPIO07: ADC3

* HiSpark Wi-Fi IoT 开发套件 OLED屏板

  * OLED屏——SSD1306芯片，I2C总线， 地址 0x78
    * GPIO13: I2C0_SDA
    * GPIO14: I2C0_SCL
  * 两个按键——ADC，接在同一个GPIO引脚上，通过电压区分
    * GPIO05: ADC2

* HiSpark Wi-Fi IoT 开发套件 环境检测板

  * 蜂鸣器——PWM控制声音的评率和音量

    * GPIO09: PWM0

  * MQ2 燃气传感器——ADC读取模拟值

    * GPIO11: ADC5

  * AHT20 温湿度传感器——I2C接口通信，地址 0x38

    * GPIO13: I2C0_SDA
    * GPIO14: I2C0_SCL

    

## 如何编译

1. 将此目录下的所有`.c`文件和`BUILD.gn`文件拷贝到，openharmony源码的`applications\sample\wifi-iot\app\iothardware`目录下，

2. 修改openharmony源码的`applications\sample\wifi-iot\app\BUILD.gn`文件，将其中的 `features` 改为：

   ```python
       features = [
           "iothardware:sensing_demo",
       ]
   ```

3. 修改`applications\sample\wifi-iot\app\iothardware\BUILD.gn` 中的`solo_demo`下的`sources`属性，放开其中的的一个文件的注释，以开启选择对应功能文件；

   ```python
       sources = [
           "colorful_light_demo.c",
           # "environment_demo.c",
           # "oled_demo.c", "oled_ssd1306.c"
       ]
   ```

4. 在openharmony源码顶层目录执行：`python build.py wifiiot`



### 报错解决

1. 编译过程中报错：undefined reference to `hi_pwm_init` 等几个 `hi_pwm_`开头的函数，
   * **原因：** 因为默认情况下，hi3861_sdk中，PWM的CONFIG选项没有打开
   * **解决：** 修改`vendor\hisi\hi3861\hi3861\build\config\usr_config.mk`文件中的`CONFIG_PWM_SUPPORT`行：
     * `# CONFIG_PWM_SUPPORT is not set`修改为`CONFIG_PWM_SUPPORT=y`

2. 编译过程中报错：undefined reference to `hi_i2c_init` 等几个 `hi_i2c_`开头的函数，

   * **原因：** 因为默认情况下，hi3861_sdk中，I2C的CONFIG选项没有打开

   * **解决：** 修改`vendor\hisi\hi3861\hi3861\build\config\usr_config.mk`文件中的`CONFIG_I2C_SUPPORT`行：
     * `# CONFIG_I2C_SUPPORT is not set`修改为`CONFIG_I2C_SUPPORT=y`



