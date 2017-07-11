# coding:utf-8
import urllib
import urllib2
import cookielib

##开始爬网页了

# response=urllib2.urlopen("http://www.sina.com.cn/")
#
# print response.read()

## 使用用户名 密码登录网页后抓取
values={}
values['username']="798288416@qq.com"
values['password']="pp19891209"
data=urllib.urlencode(values)


## 将网站cookie保存到变量
cookie=cookielib.CookieJar()
handler=urllib2.HTTPCookieProcessor(cookie)
opener=urllib2.build_opener(handler)
url="http://www.baidu.com"
reponse=opener.open(url)
for item in cookie:
    print 'Name= '+item.name
    print 'value= '+item.value



#
# request=urllib2.Request(url,data)
# print request.get_full_url()
# response=urllib2.urlopen(request)
# print response.read()


## 将网站cookie保存到文件
filename='cookie.txt'
cookie=cookielib.MozillaCookieJar(filename)
handler=urllib2.HTTPCookieProcessor(cookie)
opener=urllib2.build_opener(handler)
response=opener.open('http://www.baidu.com')
cookie.save(ignore_discard=True,ignore_expires=True)


## 从文件获取cookie并登录网站
cookie=cookielib.MozillaCookieJar()
cookie.load('cookie.txt',ignore_discard=True, ignore_expires=True)

req=urllib2.Request("http://www.baidu.com")
opener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
reponse=opener.open(req)

print reponse.read()


##利用cookie模拟网站登录
filename='cookie.txt'
cookie=cookielib.MozillaCookieJar(filename)
opener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
postdata=urllib.urlencode({'name':'name','pwd':'123'})
loginurl='http://'
result=opener.open(loginurl,postdata)
cookie.save(ignore_discard=True,ignore_expires=True)
gradeUrl='http://'
result=opener.open(gradeUrl)
print result.read()













