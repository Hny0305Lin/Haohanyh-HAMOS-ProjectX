# 为了方便开发者，使用联动函数，特此对每一个联动函数，做个解释~

> ExceedActivity 联动首页活动，获取用户输入的数据量和按钮情况，然后通过Bundle传参给下一个Activity
> ExceedRunActivity 联动工作页活动，把从Bundle里面的参数，Get后进行处理，通过Post和Get办法，进行联动。

## ExceedActivity各方法

#### onCreate() Activity中最常见方法之一

> 处理了我们输入的数据量和按钮情况，做基础的判断，然后通过tp()方法和Bundle传参到下一个Activity

``` java
protected void onCreate(@Nullable Bundle savedInstanceState) {...}
```

#### tp() ExceedActivity传参和跳转函数

> 通过Intent和Bundle俩对象，我们可以轻松的切换页面和传参

``` java
private void tp() {...}
```

#### 按钮Click事件（写入到对应XML文件里了，请结合查看）

``` java
public void TempLiandongClick(View view) {...}  /*温度Click，当Click成功时，温度1 湿度0 光照0 人体0*/
public void HumiLiandongClick(View view) {...}  /*湿度Click，当Click成功时，温度0 湿度1 光照0 人体0*/
public void LightLiandongClick(View view) {...}  /*光照Click，当Click成功时，温度0 湿度0 光照1 人体0*/
public void InfranedLiandongClick(View view) {...}  /*人体Click，当Click成功时，温度0 湿度0 光照0 人体1*/

public void XiaoyuLiandongClick(View view) {...}  /*小于Click，当Click成功时，小于1 等于0 大于0*/
public void DengyuLiandongClick(View view) {...}  /*等于Click，当Click成功时，小于0 等于1 大于0*/
public void DayuLiandongClick(View view) {...}  /*大于Click，当Click成功时，小于0 等于0 大于1*/

public void LampLiandongCheck(View view) {...}  /*台灯Click，当Click成功时，台灯1*/
public void LEDLiandongCheck(View view) {...}  /*LEDのClick，当Click成功时，LED1*/
public void FanLiandongCheck(View view) {...}  /*风扇Click，当Click成功时，风扇1*/

public void ONLiandongCheck(View view) {...}  /*开关的开，当Click成功时，开1 关0*/
public void OFFLiandongCheck(View view) {...}  /*开关的关，当Click成功时，开0 关1*/
```

#### 基础判断これExceedCheck and ExceedCheck2

> 根据此Activity的XML页面代码解读，一个为页面上半部分判断，一个为页面下半部分判断，只有两个都返回True了，那么才能处理

``` java
private boolean ExceedCheck() {...}
private boolean ExceedCheck2() {...}
```

#### 基础判断これExceedGetInformation

> 单独的数值和0和1输入时判断，目前有Bug，判断依据在代码内有设定

``` java
private int ExceedGetInformation() {...}
```

## ExceedRunActivity各方法

#### onCreate() Activity中最常见方法之一

> 处理了我们timertask进程，和通过Bundle读参，写入到全局变量供程序其他方法。

``` java
protected void onCreate(@Nullable Bundle savedInstanceState) {...}
```

#### GetSomeInformation() 与tp()方法，通过Bundle对象联动，读参后判断所需的传感器，并测试网络。

``` java
private void GetSomeInformation() {...}
```

> 里面得到的int，几乎都会写入到全局变量，并且结果（100%）只有1 和 0。

``` java
    int sensor1 = 0;
    int sensor2 = 0;
    int sensor3 = 0;
    int sensor4 = 0;
    int xiaoyu = 0;
    int dengyu = 0;
    int dayu = 0;
    int useI = 0;
    int useII = 0;
    int useIII = 0;
    int booloff = 0;
    int boolon = 0;
    public int usenum;
    public int usebijiao;
    public int useonoroff;
    int gnum = 0;
    int gIorO = 0;
```

``` java
    sensor1 = bundle.getInt("sensorI");
    sensor2 = bundle.getInt("sensorII");
    sensor3 = bundle.getInt("sensorIII");
    sensor4 = bundle.getInt("sensorIV");
    xiaoyu = bundle.getInt("bijiaoI");
    dengyu = bundle.getInt("bijiaoII");
    dayu = bundle.getInt("bijiaoIII");
    useI = bundle.getInt("useI");
    useII = bundle.getInt("useII");
    useIII = bundle.getInt("useIII");
    booloff = bundle.getInt("booloff");
    boolon = bundle.getInt("boolon");
    gnum = bundle.getInt("num");
    gIorO = bundle.getInt("IorO");

    int usenum = Checkusenum();
    int usebijiao = Checkusebijiao();
    int useonoroff = Checkuseonoroff();
```
#### Checkusenum() \ Checkusebijiao() \ Checkuseonoroff() 通过重要判断，得知所需家电控制、大于等于小于的触发条件、开关条件

> 返回值int，返回情况在下方↓

``` java
int Checkusenum() {...}     /*3个家电控制，7种触发排列结果*/
/*
1为台灯     2为LED      3为台灯、lED        4为风扇     5为台灯、风扇       6为LED、风扇        7为全部
*/

int Checkusebijiao() {...}      /*正常比较，3种结果*/
/*
1为小于     2为等于     3为大于
*/

int Checkuseonoroff() {...}     /*正常比较，2中结果*/
/*
1为ON       2为OFF
*/
```

#### Huawei() 浩瀚银河自研，与HuaweiCloud沟通方法

> 联动代码与其他板子对应功能页，不一样，不一样！！！我们调整了代码

``` java
void Huawei(String project_id, String device_id, int a, int usenum, int usebijiao, int useonoroff) {...}
```

> project_id 为 项目ID

> device_id 为 硬件ID

> 以上两个String，是在华为云IoTDA里面设置的值！！！不是自己写的！！！

> a 为 使用的传感器是什么

> usenum 为 Checkusenum()返回所需家电控制结果

> usebijiao 为 Checkusebijiao()返回所需触发条件结果

> useonoroff 为 Checkuseonoroff()返回所需触发开关要求结果

> a、usebijiao、usenum，是丢到里面if比较的，比较成功了，直接把结果，传给ReadySend方法（带上useonoroff）

> a、usenum，同时也是搞定需要展示数据在APP上面的重要参数。

#### ReadySend() 准备开始自动化网络通讯的方法

``` java
void ReadySend(int sensor,int b) {...}
```

> 自带6个函数，需要开发者修改

> project_id 为 项目ID IoTDA主页————API检索与调试————随便点一个API，就能看到项目ID了。

> 也可以通过https://support.huaweicloud.com/api-iothub/iot_06_v5_1001.html  获取项目ID

> device_id 为 硬件ID IoTDA主页————设备接入————设备————所有设备————设备ID那一栏，就有复制按钮了

> service_id 为 服务ID IoTDA主页————设备接入————产品————产品名称————查看，就能看到服务ID了

> command_name 为 命令名字 IoTDA主页————设备接入————产品————产品名称————查看，就能看到命令名字了

> command_param 为 下发参数 IoTDA主页————设备接入————产品————产品名称————查看，就能看到下发参数了

> command_value 为 响应参数 一般为ON、OFF

``` java
    String device_id = "";
    String service_id = "";
    String command_name = "";
    String command_param = "";
    String command_value = "";

    String project_id = "******";
```

> 函数工作情况如下：当sensor（家电）为1（LED灯）时，确认硬件ID和服务ID为LED的，控制名字为XXXXX，当b（开关结果）为1（开）时，LED灯开。
> 以下为实现代码例子：

``` java
    if(sensor == 1) {
        device_id = "625d28cfecf9c41c38216abe_2454refwefw428";
        service_id = "Agriculture";
        command_name = "Agriculture_Control_light";
        if(b == 1) {
            command_param = "Light";
            command_value = "ON";
            ControlSenderHUAWEI(project_id,device_id,service_id,command_name,command_param,command_value);
            Log.w("TAG","LED开");
        }
    }
```

#### ControlSenderHUAWEI() 正在开始自动化网络通讯的方法

``` java
private void ControlSenderHUAWEI(String project_id,String device_id,String service_id,String command_name,String command_param,String command_value) {...}
```

##### 与ReadySend()联动，传入6个String值，然后开始网络通讯。

**网络通讯，使用Request、OkHttpClient对象，写法都在代码上面了**

**因为已经算开箱即用了（On和Off我们做判断过了，所以这段代码基本上复制粘贴就能当一个直接对象使用！）基本上除了网络问题造成的通讯不成功，就没有问题了**

#### 剩下的网络通讯方法：get() 和 post()

``` java
private String get(String url) {...}
private void post() {...}
```

> get仅仅是用于很上面void Huawei()使用了，用途就是把传感器信息显示到APP上。

> post很重要，可获取HUAWEITOKEN，和ControlSenderHUAWEI()联动，服务网络连接代码。

**华为X-Auth-Token，需要获取，Token大约有效时间为24H内，不过我们项目设置成1次运行获取1次，下一次运行也会获取，所以没有问题。**

**同时经历了多次开发，让我们明白：华为Token获取没有限制时间内次数。**































