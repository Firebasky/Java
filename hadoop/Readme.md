# Hadoop

[【安全风险通告】Apache Hadoop Yarn RPC未授权访问漏洞安全风险通告](https://mp.weixin.qq.com/s?__biz=MzU5NDgxODU1MQ==&mid=2247495027&idx=1&sn=5758a6717309a55e09f184e5bae82c75&chksm=fe79c9ebc90e40fd6d0c3f0bd21ce92f53b4f58aa0ee07d0c005ca85a28d2cfd70f61c40fae7&mpshare=1&scene=23&srcid=1123jW67UF5RY5e5aOeDZ5ha&sharer_sharetime=1637638003307&sharer_shareid=33a823b10ae99f33a60db621d83241cb#rd)

[Hadoop Yarn RPC RCE 复现](https://mp.weixin.qq.com/s/lVl5HnVuZyLTIeSrbw1cuA)

[Hadoop Yarn RPC未授权RCE（含一键利用工具）](https://mp.weixin.qq.com/s?__biz=MzkwNDI1NDUwMQ==&mid=2247485150&idx=1&sn=c31937fdb3e92ae3951a98b7967032b2&chksm=c0888394f7ff0a8224a8984f2cb4935f9aa1e7d243c4b512c488600d8fef0b6ec16a2b345865&token=616099468&lang=zh_CN#rd)

[Hadoop Yarn RPC未授权访问漏洞复现](https://zgao.top/hadoop-yarn-rpc%E6%9C%AA%E6%8E%88%E6%9D%83%E8%AE%BF%E9%97%AE%E6%BC%8F%E6%B4%9E%E5%A4%8D%E7%8E%B0/)

[GHSL-2022-012: Arbitrary file write during TAR extraction in Apache Hadoop - CVE-2022-26612](https://securitylab.github.com/advisories/GHSL-2022-012_Apache_Hadoop/)

## 环境搭建

org.apache.hadoop.yarn.util.resource.ResourceUtils

```
docker pull kpli0rn/hadoop-rpc-vuln:3.3.0
docker run -d --name yarn -p 8042:8042 -p 8032:8032 kpli0rn/hadoop-rpc-vuln:3.3.0
```

