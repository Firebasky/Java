# RASP

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
