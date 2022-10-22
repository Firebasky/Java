# chunked-coding-converter

[唯快不破的分块传输绕WAF](https://mp.weixin.qq.com/s/pM1ULCqNdQwSB7hcltrbtw)

[Bypass WAF HTTP协议覆盖+分块传输组合绕过](https://mp.weixin.qq.com/s/2DDYyvsZ5HIQC0qGMK9znQ)

[利用分块传输吊打所有WAF](https://mp.weixin.qq.com/s/eDiiiVX4oF0LYG3Ia5P4mw)

[技术讨论 | 在HTTP协议层面绕过WAF](https://www.freebuf.com/news/193659.html)

[编写Burp分块传输插件绕WAF](https://gv7.me/articles/2019/chunked-coding-converter/)

[Java反序列化数据绕WAF之延时分块传输](https://gv7.me/articles/2021/java-deserialized-data-bypasses-waf-through-sleep-chunked/)

```
只有HTTP/1.1支持分块传输
POST包都支持分块，不局限仅仅于反序列化和上传包
Transfer-Encoding: chunked大小写不敏感
```

