# 概述

> github地址：[https://github.com/Double2hao/StudyFloatUtil](https://github.com/Double2hao/StudyFloatUtil)

在多端交互的场景中，客户端往往只扮演着平台的角色。
最常见的如webview，小程序等等——客户端虽然是最终程序的展现平台，但是逻辑却不会由客户端的工工程师来直接开发，而是由前端的工程师开发完后运行在客户端上。
由于客户端的功能是由客户端工程师提前开发好后提供给前端的工程师调用，因此这些功能一旦出现问题，那么就需要客户端工程师和前端工程师一起联调沟通解决。
类似于这样的场景中，为了减少开发和沟通成本，一般就需要有个工具来辅助前端的工程师定位问题或者直接debug。

“Android日志工具”可以说是方案之一，主要逻辑如下：
客户端预先在关键的节点处埋下日志，或者说提供给前端打日志的方式，这样前端工程师就可以独立定位问题甚至解决问题。

### demo如下
<img src="https://img-blog.csdnimg.cn/20201017112334187.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RvdWJsZTJoYW8=,size_16,color_FFFFFF,t_70" width = 30% height = 30% /> <img src="https://img-blog.csdnimg.cn/20201017112109354.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RvdWJsZTJoYW8=,size_16,color_FFFFFF,t_70" width = 30% height = 30% />

# 关键思路
### 为什么使用悬浮窗
笔者前期也考虑过使用popupwindow等展现形式。
popupwindow的好处主要是“不需要悬浮窗权限”、“更加轻量”等。
但是popupwindow也有明显的劣势，比如“在多个页面跳转显示就需要有多个，而用户行为无法预估”，“多进程之间log不好维护”等。

最终考虑到这个 日志工具更多的还是只会开发者内部使用，申请权限的影响不大。其在多进程、多页面的情况下记录和展示log也更加方便。
于是这个日志工具 就选择使用“悬浮窗”的形式。
### 分成多个步骤“初始化”、“设置开关”、“悬浮窗开关”
在实际场景中，这样的调试功能，开发者往往只期望在特定场景下触发，如在测试环境中，在测试包中等等。
因此需要分成多个步骤让开发者自己来控制“是否初始化”、“是否记录log”等。
### 使用广播来记录log
实际场景中可能存在多个进程的情况，使用广播可以保证多个进程的log都能够收集到。

