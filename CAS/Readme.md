# CAS

>[单点登录](https://baike.baidu.com/item/%E5%8D%95%E7%82%B9%E7%99%BB%E5%BD%95/4940767)

## 4.1.x-4.1.6
4.1.7版本之前存在一处默认密钥的问题，利用这个默认密钥我们可以构造恶意信息触发目标反序列化漏洞，进而执行任意命令。

类似于shiro550

并且版本存在**Commons-collections4**

## 4.1.7-4.2.x
这个版本的key默认是随机生成的,需要攻击者提供。

https://www.anquanke.com/post/id/198842

并且存在c3p0组件

## 回显

https://www.00theway.org/2020/01/04/apereo-cas-rce/

## PaddingOracle
https://lfysec.top/2020/06/01/ApereoCAS-PaddingOracle%E6%BC%8F%E6%B4%9E%E5%88%86%E6%9E%90/

## xxe
https://lfysec.top/2020/06/01/ApereoCAS-PaddingOracle%E6%BC%8F%E6%B4%9E%E5%88%86%E6%9E%90/



