# MyBatis

+ [CVE-2020-26945 mybatis二级缓存反序列化的分析与复现](https://mp.weixin.qq.com/s?__biz=MzUzNTEyMTE0Mw==&mid=2247484196&idx=1&sn=735666b28cff6e6552d8f3e16b1be9a5&chksm=fa8b1ebccdfc97aa80b6103587fd418b63c6b0d290cd4229ccc999b3706fe4f325595049a7ce&mpshare=1&scene=23&srcid=1013pFDy9OUsVb24733hEAhA&sharer_sharetime=1602582161965&sharer_shareid=8a8448ee03016e30de742559b7359a01%23rd)  简单的说就是mybatis为了缓解多次查询而开启的缓存，如果可以修改缓存的内容就可以将其反序列化。[CVE-2020-26945漏洞](https://www.anquanke.com/post/id/219457)
