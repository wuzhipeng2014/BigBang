#encoding:utf-8

from functools import reduce

## 将一个str拆分为list(map函数)
## 对一个可迭代的对象中的每一个迭代对象应用函数f处理
s='abcd'
l=list(map(str,s))

print(l)

## 使用reduce函数求对list进行求和
def add(x,y):
    return x+y

sumResult=reduce(add,[1,2,3,4,5])

print(sumResult)

c='100'
print(list(map(int,c)))

##使用map reduce函数实现str转int
def fn(x,y):
    return int(x)*10+int(y)
n='1234'
result=reduce(fn,map(int,n))

print(result)









