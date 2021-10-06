# java危险函数

```java
密码硬编码、密码明文存储: password、pass、jdbc
```

```java
XSS: getParamter、<%=、param.
```

```java
SQL 注入: Select、Dao 、from 、delete 、update、insert
```

```java
任意文件下载: download 、fileName 、filePath、write、getFile、getWriter
```

```java
文件上传: Upload、write、fileName 、filePath
```

```java
任意文件删除: delete 、deleteFile、fileName、filePath
```

```java
命令注入: getRuntime、exec、cmd、shell GroovyShell.evaluate
```

```java
ssrf: HttpClient.execute()、HttpClient.executeMethod()、HttpURLConnection.connect、HttpURLConnection.getInputStream、URL.openStream、HttpServletRequest、BasicHttpEntityEnclosingRequest、DefaultBHttpClientConnection、BasicHttpRequest、
```

```java
缓冲区溢出: strcpy,strcat,scanf,memcpy,memmoGetc(),fgetc(),getchar;read,printf
```

```java
XML 注入: DocumentBuilder、XMLStreamReader、SAXBuilder、SAXParser
SAXReader 、XMLReader
SAXSource 、TransformerFactory 、SAXTransformerFactory 、
SchemaFactory
```

```java
反序列化漏洞: 反序列化操作一般在导入模版文件、网络通信、数据传输、日志格式化存储、对象数据落磁盘或DB 存储等业务场景,在代码审计时可重点关注一些反序列化操作函数并判断输入是否 
    
ObjectInputStream.readObject
、ObjectInputStream.readUnshared、XMLDecoder.readObject
Yaml.load 、 XStream.fromXML 、 ObjectMapper.readValue 、
JSON.parseObject
```

```java
日志记录敏感信息: log.info logger.info
```

```java
URL跳转: sendRedirect、setHeader、forward
```

```java
敏感信息泄露及错误处理: Getmessage、exception
```

```java
不安全组件暴露:AndroidManifest.xml
通过查看配置文件 AndroidManifest.xml,查看<inter-filter>属性有没有配置 false
```

