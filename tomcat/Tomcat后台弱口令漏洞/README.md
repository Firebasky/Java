# Tomcat后台弱口令漏洞

Tomcat后台存在弱口令，进入网站后点击登录然后使用burp进行爆破测试

可以发现账户密码是利用Authorization该授权字段以base64方式传递账户信息的
发现加密方式后，拿去解密后发现他的数据传输是将账户与密码用冒号进行组合之后在用base64加密所传递的。构造字段进行爆破
使用burp抓包后发送到 Intrude 模块进行暴力破解

成功爆破出账号密码，然后使用base64解码得出明文账号密码
使用爆破出的账号密码登录进去后台后发现有一个上传页面，直接上传一个war木马就可以

可以使用python进行暴力破解或者是bp
这里可以使用kali的msf模块

```
use auxiliary/scanner/http/tomcat_mgr_login
set rhosts ip
set rport port 
```
