# Haohanyh-HAMOS-ProjectX
Haohanyh-HAMOS-ProjectX是我们的一个LiGHTs计划中的部分，目的就是开源我们在hi3861上与物联网（智能家居）上做的探索。

# 使用到的硬件 和 硬件重要资料
### Hi3861 BearPi小熊派 小型主板
> 购买地址:[tb](https://item.taobao.com/item.htm?spm=a1z10.5-c-s.w4002-22244473708.15.59475f69JPXE93&id=633296694816)
> 芯片资料:[点我下载](https://gitee.com/bearpi/bearpi-hm_nano/raw/master/applications/BearPi/BearPi-HM_Nano/docs/board/BearPi_HM%20Nano%20%E8%8A%AF%E7%89%87%E6%89%8B%E5%86%8C.pdf)
> 有关资料:[gitee点我查看](https://gitee.com/bearpi/bearpi-hm_nano)

### Hi3881 BearPi小熊派 带屏+STM32处理器开发板
> 购买地址:[tb重新上架，价格不变但可以携带E53案例板了](https://item.taobao.com/item.htm?id=662078665554)
> 芯片资料:[点我下载](https://github.com/Hny0305Lin/Haohanyh-HAMOS-ProjectX/raw/master/HaohanyhHAMOSProjectX%20Resource/BearPi/%E9%B8%BF%E8%92%99%C2%B7%E5%8F%94%20Micro/STM32MP157A.PDF)
> 原理图:[点我下载](https://gitee.com/bearpi/bearpi-hm_micro_small/blob/master/applications/BearPi/BearPi-HM_Micro/docs/board/BearPi-HM%20Micro%E5%8E%9F%E7%90%86%E5%9B%BE.pdf)
> 有关资料:[gitee点我查看南向硬件开发](https://gitee.com/bearpi/bearpi-hm_micro_small) [gitee点我查看北向APP开发](https://gitee.com/bearpi/bearpi-hm_micro_app)

### Hi3861 Hihope润和Wifiiot 开发板
> 购买地址:[tb](https://item.taobao.com/item.htm?id=622343426064)
### Hi3861 Hihope润和Wifiiot 资料系列:

| 资料信息 | 文件情况 | 下载地址 |
|:----|:----:|:----:|
| 芯片资料 | *Not Found* | 受限制 或 找不到 |
| 有关资料 | Link to Gitee | [gitee点我查看](https://gitee.com/hihopeorg/HarmonyOS-IoT-Application-Development) |
| 温湿度传感器 AHT20 | PDF | [点我下载](https://github.com/Hny0305Lin/Haohanyh-HAMOS-ProjectX/raw/master/HaohanyhHAMOSProjectX%20Resource/Hihope/AHT20/AHT20_%E6%B8%A9%E6%B9%BF%E5%BA%A6%E4%BC%A0%E6%84%9F%E5%99%A8.PDF) |
| OLED屏幕板 | PDF + **C Header** + **C Source** | [点我下载](https://github.com/Hny0305Lin/Haohanyh-HAMOS-ProjectX/tree/master/HaohanyhHAMOSProjectX%20Resource/Hihope/SSD1306) |
| Hi3861 | PDF | [点我下载](https://github.com/Hny0305Lin/Haohanyh-HAMOS-ProjectX/tree/master/HaohanyhHAMOSProjectX%20Resource/Hihope/Hi3861_MotherBoard_And_CH340G) |
| CH340G | PDF | [点我下载](https://github.com/Hny0305Lin/Haohanyh-HAMOS-ProjectX/tree/master/HaohanyhHAMOSProjectX%20Resource/Hihope/Hi3861_MotherBoard_And_CH340G) |

### 国内外网站发布的文章地址

| 文章地址汇总 | 链接 |
|:----|----:|
|51cto文章地址：|[点我访问](https://ost.51cto.com/posts/12170)|
|电子发烧友地址:|[点我访问](https://bbs.elecfans.com/jishu_2277832_1_1.html)|
|知乎文章:|[点我访问](https://zhuanlan.zhihu.com/p/510892259)|
|*New!* V2EX:|[点我访问](https://v2ex.com/t/854406)|
|*New!* 浩瀚银河Hexo:|[点我访问](https://hexo.haohanyh.com/2022/06/01/HAMOS-2022-New)|
|*Stay tuned!* 解决方案 来源Github:|[7月见面]()|

# 首个采用浩瀚银河的自有中文开源协议项目...

🔝Haohanyh Computer Software Products Open Source LICENSE

> 下载地址1：https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE
>
> 下载地址2: https://git.haohanyh.top:3001/Haohanyh/LICENSE

# 首个鸿蒙硬件操作系统与Android APP的结合项目...

### 智能家居(Hihope润和开发板温湿度 + 小熊派智能路灯(家庭灯系) + 小熊派人体感应(门前感应) + 小熊派智能物流(GPS小车定位) + 小熊派智能农业(温湿度采集和LED转子控制) + 小熊派Micro板(基础通讯) + 润和AICamera板(通讯+尝试技术联动))

目前完工情况：

* 小熊派智能路灯(家庭灯系)：完成通过华为云IoTDA控制，正在开发自动化联动控制。
* ~~小熊派人体感应(门前感应)：完成通过华为云IoTDA上报，配合小熊派智能路灯开发板完成自动化联动控制。~~
* 小熊派智能物流(GPS小车定位)：完成通过华为云IoTDA上报，配合小熊派智能路灯开发板完成自动化联动控制。
* ~~小熊派智能农业(温湿度采集和LED转子控制)：已经Over了~~
* ~~小熊派开发板多联动页面(自定义自动化页面)：除了人体我们没时间开发了，需要赶校内进度外，其他功能OK。~~
* **小熊派智能家居图表页面**：**目前我们不会在这个功能上做开发!**
* ~~主页UI：已经Over了~~

《折叠板屏线断了，正在返修中，目前Micro板技术路线，停止开发!》

距离完工进度(百分比和更新情况)：

|  使用到的Hi3861开发板   | 完工程度  | 后续更新情况 |
|:----|:----:|:----:|
| Hihope润和开发板温湿度✅  | 100%✔️  | 后续无更新 |
| 小熊派智能路灯(家庭灯系)🈴  | 75%❓ | 3.0版本时会更新 |
| 小熊派人体感应(门前感应)✅  | 100%✔️ | 暂时无更新 |
| 小熊派智能物流(GPS小车定位)🉑  | 80%❓ | 近期准备更新（Gugugu~~） |
| 小熊派智能农业(温湿度采集和LED转子控制)✅ | 100%✔️ | 后续无更新 |
| 小熊派开发板多联动页面(自定义自动化页面)🉑 | 99%✔️ | **芜湖~** |
| 主页UI❇️ | 100%✔️ | 后续无更新 |
| 润和AICamera板(尝试技术路线)🈳 | 0%⁉️ | 未来更新 |
| 小熊派MicroSmall版板(3881和3861差距不大)🈳 | 0%⁉️ | 未来更新 |
| **小熊派智能家居图表页面**🈳 | 0%🚫 | **未来更新** |

| 软件开发情况 | 完工程度 | 后续更新情况 |
|:----|:----:|:----:|
| Android APP | 92.5% | 三期结束，目前计划结束一段时间，暑期继续维护。 |
| HarmonyOS HAP | 0% | 准备开始，并且暑期与Android APP联动发展! |
| Windows EXE | 0% | 计划中... |

Android APP对应版本号:

| 版本号 | 对应开发版本号 | 发布日期 |
|:----|:----:|:----:|
| 3.0.120 | ⭐Aurora | 2022.5.14 |
| 4.1.6 | ⛰️Basin | 2022.6.22 |
| ?.?.??? | ☄️Comet | ????.??.?? |
| ?.?.??? | 🏜️Desert | ????.??.?? |
| ?.?.??? | 🌐Earth | ????.??.?? |
| ...... | ...... | ...... |

HarmonyOS HAP对应版本号:

| 版本号 | 对应开发版本号 | 发布日期 |
|:----|:----:|:----:|
| ?.?.??? | ⭐Aurora | ????.??.?? |
| ?.?.??? | ⛰️Basin | ????.??.?? |
| ?.?.??? | ☄️Comet | ????.??.?? |
| ?.?.??? | 🏜️Desert | ????.??.?? |
| ?.?.??? | 🌐Earth | ????.??.?? |
| ...... | ...... | ...... |

作为浩瀚银河首次在鸿蒙操作系统开发上，交出的一份答卷，我们有信心，有能力，让这样的项目成为我们全新的起点。

> 项目（短暂结束）
>
> 项目预估5月中旬~6月间完工，届时将成为一份**100%完工、100%开源**的完美作业。
> 项目三期工作结束，已完工，目前维护暂停，敬请期待暑期维护和近期解决方案、MD教程！！！
>
> 让这份作业，送给鸿蒙开发者、浩瀚银河粉丝、51CTO、电子发烧友等开发者、用户群体。
>
> 我们相信技术驱动生活就是现在，我们相信未来的事情就在眼前。

# 项目使用到的开源协议

### Copyright (c) 2020 Nanjing Xiaoxiongpai Intelligent Technology Co., Ltd.
使用开源协议：Apache-2.0

### Copyright (c) 2020, HiHope Community.
使用开源协议：未知

### Copyright (c) OpenHarmony-SIG
使用开源协议：Apache-2.0
介绍: OpenHarmony SIG组织，用于孵化OpenHarmony相关开源生态项目
代码开源地址: [点我进入](https://gitee.com/openharmony-sig/knowledge_demo_smart_home)

### Copyright (c) 2020, Siwei Xu
使用开源协议：BSD 3-Clause License

### Copyright 2022 bejson.com
使用开源协议：未知

### 版权所有 (C) 2019-FUTURE 浩瀚银河，版权所有。
### HAMOS 使用 浩瀚银河计算机软件产品源代码开放协议
**永久开源，二次开发请署名“浩瀚银河”**
使用开源协议：Haohanyh Computer Software Products Open Source LICENSE

# 小小的须知（不知道也没关系）

🆓 此项目是开源的，可二次开发~

> 本项目，只需要遵守浩瀚银河开源协议，和部分作者协议，即可开源OK！

🆒 项目正努力往完美方向发展，需要时间的积累~

> 本项目目前还是正在更新中版本，但是就目前而言开源的代码已经很Cool了所以请麻烦给个⭐ 、🍴 、👁️三连支持吧
>
> （Star是对我们最棒的支持~，Fork是对你们最好的功能~，Watch可随时订阅我们的开源状况）

# 关于捐赠

浩瀚银河的此开源项目，目前并不需要各位的捐赠，各位也无需担心项目维护问题，我们会努力在鸿蒙硬件操作系统上面，唱出我们优秀的作品！

# 关于商业化该项目

请取得部分未使用开源协议的作者的同意，即可将该项目商业化。

# 送给2022年的今天...

🙏[浩瀚银河](https://hexo.haohanyh.com/2022/01/17/2022-ByeByeCOVID-19/)衷心祝愿新型冠状病毒早日结束传播。向每一位👨‍⚕️👩‍⚕️医务工作者、👨‍💼👩‍💼线上开发者，致以最高的敬意👍