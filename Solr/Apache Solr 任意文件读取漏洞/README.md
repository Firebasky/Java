# Apache Solr 任意文件读取漏洞

## 漏洞描述

Apache Solr 存在任意文件读取漏洞，攻击者可以在未授权的情况下获取目标服务器敏感文件

## 漏洞影响

Apache Solr <= 8.8.1

## 搜索

```
FOFA：Apache Solr <= 8.8.1
```

## 漏洞复现
见exp

## exp

+ 我们的目的是通过信息泄露获得我们想要的路径
+ 然后通过路径去访问xxx/config，并且设置配置文件`{"set-property" : {"requestDispatcher.requestParsers.enableRemoteStreaming":true}}`
+ 最后通过`/solr/xxxx/debug/dump?param=ContentStreams`的post方法去读文件。
