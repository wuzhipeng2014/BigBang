#encoding:utf-8

## for循环实现迭代

for c in 'abc':
    print(c)

## 遍历dict
d={'a':1,'b':2,'c':3}
#遍历key
for k in d:
    print(k)

#遍历value
for v in d.values():
    print(v)


#遍历key和value
for k,v in d.items():
    print(k,v)


## 判断某个对象是否是可迭代对象

from collections import Iterable
print(isinstance('abc',Iterable))

## 使用下标的方式遍历list
for i,v in enumerate(list(range(0,5))):
    print(i,v)

print('## for循环中同时引用两个变量')
for x,y in [(1,1),(2,4),(3,9)]:
    print(x,y)











