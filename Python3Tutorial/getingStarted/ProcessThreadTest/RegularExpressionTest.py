#encoding:utf-8

## 正则表达式匹配固话号码

import re

pat=r'^\d{3}-\d{3,8}'

s=re.match(pat,'010-12345')
if  s:
    print('匹配成功')
    print(s)
else:
    print('匹配失败')


s=re.match(pat,'010 12345')
if  s:
    print('匹配成功')
    print(s)
else:
    print('匹配失败')


## 切分字符串
split=re.split(r'[\s\,]+','a,b, c   d')

print(split)

split=re.split(r'\s+','a b   c')

print(split)


## 正则表达式分组提取字符串
m=re.match(r'^(\d{3})-(\d{3,8})$','010-12345')
print('所有匹配项为: ',m.group(0))

print('区号为: ',m.group(1))

print('本地号码为:',m.group(2))



##匹配时间格式
t='19:05:30'
m = re.match(r'^(0[0-9]|1[0-9]|2[0-3]|[0-9])\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|[0-9])\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|[0-9])$', t)

print(m.groups())


##贪婪匹配
m=re.match(r'^(\d+)(0*)$','1002300')
print(m.groups())

##最小匹配
m=re.match(r'^(\d+?)(0*)$','1002300')
print(m.groups())

##先编译正则表达式,然后直接用于匹配
re_telephone=re.compile(r'^(\d{3})-(\d{3,8})$')

groups=re_telephone.match('101-12345').groups()

print(groups)


##匹配邮件的正则表达式
re_mail=r'([\w.]+?)@([\w]+?).com'
compiled_mail= re.compile(re_mail)
result=compiled_mail.match('zhipeng.wu@qunar.com').groups()


print(result)


