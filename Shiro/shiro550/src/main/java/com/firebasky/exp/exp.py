import requests

burp0_url = "http://127.0.0.1:8080/"
burp0_cookies = {"JSESSIONID": "F9644B7DD50F08A6DEF9BD1B22B3999F", "rememberMe": "MgsckemDZpFVm8IxNR19ykC58oTyhbFbQYYuCGKoTW+//7y5HqUuZ52WaCyBzV2Rge7kJkN/ZvMqIIMI7CW04mzYPOJRnTANUWy7i+ijZeCMhdcLo30AwveuynGTuqdu39IDMZqgpSf63NpxcwZUMKd7ul6M4gPkoqaklZDJaMlEdwu/2l6hFtgEMfSq+/63"}
burp0_headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0", "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8", "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2", "Accept-Encoding": "gzip, deflate", "Connection": "close", "Upgrade-Insecure-Requests": "1"}
r=requests.get(burp0_url, headers=burp0_headers, cookies=burp0_cookies)
print(r.headers)