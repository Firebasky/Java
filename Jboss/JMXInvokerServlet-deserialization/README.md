# JBoss JMXInvokerServlet 反序列化漏洞

这是经典的JBoss反序列化漏洞，JBoss在/invoker/JMXInvokerServlet请求中读取了用户传入的对象，然后我们利用Apache Commons Collections中的Gadget执行任意代码。

**利用思路和CVE-2017-12149 差不多**
