
## 如果Java项目存在写文件操作怎么rce?
### 1.普通的Java web项目 
直接写jsp木马 （如果能解析

### 2.如果不能解析jsp
通过写class文件让其触发某一个方法然后重写该方法rce。也就是把恶意类写入classpath( target/classes)，再通过某种方式加载、使用该恶意类，触发该恶意类的static代码块或执行该恶意类的某个方法，来实现通用的RCE利用。重点关注class.formane
可以参考2021国赛的ezj4va 就是通过重写readobject方法去触发rce。
或者可以参考d3ctf中的no rce题中也是写入target/classes中实现静态方法。然后通过jdbc去初始化恶意类触发。

### 3.如果项目是jar打包启动的
面前自己遇到的是springboot项目 通过覆盖charset.jar去hook实现rce。大概原理就是jvm启动的过程中不会全部加载资源如charset.jar是不会加载的只有通过特点方法才会加载。这样可以减少Java内存的消耗。
参考 文章springboot写文件rce

>
https://www.cnblogs.com/wh4am1/p/14681335.html

https://mp.weixin.qq.com/s?__biz=MzI3MzUwMTQwNg==&mid=2247485312&idx=1&sn=22dddceccf679f34705d987181a328db&token=1393640502&lang=zh_CN&scene=21#wechat_redirect
