# encoding:utf-8

## 函数调用

## 十六进制转换
a=100
b=hex(a)
print(b)


## 函数定义

def fun(x):
    if x>=0:
        return x
    else:
        return -x


print(fun(-10))


# 指定函数的默认参数
def powern(x,n=2):
    while(n>1):
        result=x*x
        n=n-1
    return result


## 可变参数

def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum
# 调用方法
print(calc(1,2,3))

##关键字参数
def person(name, age, **kw):
    print('name:', name, 'age:', age, 'other:', kw)

#调用方法
person('Bob',25,city='北京')

##命名关键字参数(只接收特定名字的关键字参数)
def person(name,age,*,city,job):
    print(name,age,city,job)

person('James',28,city='北京',job='programer')



##参数组合
#参数定义的顺序必须是：必选参数、默认参数、可变参数、命名关键字参数和关键字参数。
#

def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)

def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)


#函数调用
f1(1, 2, 3, 'a', 'b', x=99)
f2(1, 2, d=99, ext=None)


##函数总结

# 小结
#
# Python的函数具有非常灵活的参数形态，既可以实现简单的调用，又可以传入非常复杂的参数。
#
# 默认参数一定要用不可变对象，如果是可变对象，程序运行时会有逻辑错误！
#
# 要注意定义可变参数和关键字参数的语法：
#
# *args是可变参数，args接收的是一个tuple；
#
# **kw是关键字参数，kw接收的是一个dict。
#
# 以及调用函数时如何传入可变参数和关键字参数的语法：
#
# 可变参数既可以直接传入：func(1, 2, 3)，又可以先组装list或tuple，再通过*args传入：func(*(1, 2, 3))；
#
# 关键字参数既可以直接传入：func(a=1, b=2)，又可以先组装dict，再通过**kw传入：func(**{'a': 1, 'b': 2})。
#
# 使用*args和**kw是Python的习惯写法，当然也可以用其他参数名，但最好使用习惯用法。
#
# 命名的关键字参数是为了限制调用者可以传入的参数名，同时可以提供默认值。
#
# 定义命名的关键字参数在没有可变参数的情况下不要忘了写分隔符*，否则定义的将是位置参数。

