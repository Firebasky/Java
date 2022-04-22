# RASP

Runtime application self-protection

它是一种新型应用安全保护技术，它将保护程序像疫苗一样注入到应用程序中，应用程序融为一体，能实时检测和阻断安全攻击，使应用程序具备自我保护能力，当应用程序遭受到实际攻击伤害，就可以自动对其进行防御，而不需要进行人工干预。



RASP技术可以快速的将安全防御功能整合到正在运行的应用程序中，它拦截从应用程序到系统的所有调用，确保它们是安全的，并直接在应用程序内验证数据请求。Web和非Web应用程序都可以通过RASP进行保护。该技术不会影响应用程序的设计，因为RASP的检测和保护功能是在应用程序运行的系统上运行的。

### 使用

https://github.com/baidu/openrasp

安装：java -jar RaspInstall.jar -install tomcat目录

配置环境变量： 新建 CATALINE_HOME 值为tomcat目录

在tomcat目录的bin下执行cataline.bat run

测试curl -v 127.0.0.1:8888 |grep OpenRASP

服务器的响应 X-Protected-By: OpenRASP

### 测试

https://github.com/baidu-security/openrasp-testcases

日志:\rasp\logs\alarm\alarm.log

### 实现

java中是通过Java Agent方式进行实现

**是通过java的agent配合asm对运行的字节码进行了修改，这样就达到了埋点hook的目的。**

PHP是通过开发第php扩展库来进行实现。

.NET是通过IHostingStartup（承载启动）实现



**RASP技术其实主要就是对编程语言的危险底层函数进行hook**，毕竟在怎么编码转换以及调用，最后肯定会去执行最底层的某个方法然后对系统进行调用。由此可以反推出其hook点，然后使用不同的编程语言中不同的技术对其进行实现。



### 学习

https://blog.csdn.net/HY1273383167/article/details/116211211   1  

https://blog.csdn.net/u011721501/article/details/74990346   1

https://www.freebuf.com/articles/web/197823.html

https://paper.seebug.org/1041/

https://blog.csdn.net/u011721501/article/details/74990346

https://paper.seebug.org/330/


