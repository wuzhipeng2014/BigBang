#encoding:utf-8

from collections import Iterable

## 判断对象是否是可迭代的
resulst=isinstance([],Iterable)

print(resulst)


## iterator的定义:
# 可以被next()函数调用并不断返回下一个值的对象被称为迭代器: Iterator


## list, dict , str 虽然是iteratable,但是并不是迭代器
## 可以使用iter函数将list, dict, str变成迭代器

from collections import Iterator
result=isinstance(iter([]),Iterator)
print(result)

## 注意:
## Iterator是一个惰性计算的序列

##python中的for循环就是不断代用next()函数实现的




