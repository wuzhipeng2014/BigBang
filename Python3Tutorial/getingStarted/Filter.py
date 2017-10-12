#encoding:utf-8

## filter过滤list中所有的奇数
def is_odd(n):
    return n%2==1
print(list(filter(is_odd,[1,2,3,4,5,6,7])))

## 把一个list中的所有空串删除
def not_empty(s):
    return s and s.strip()

## 注意:　filter函数的返回值类型是一个Iterator,就是一个惰性序列,要强迫filter完成计算,需要用
## list()函数获取所有结果并返回list
result=list(filter(not_empty,['A', '', 'B', None, 'C', '  ']))
print(result)

##filter求素数
##奇数生成器
def odd_iter():
    n=1
    while True:
        n=n+2
        yield n

## 素数筛选函数
def not_divisible(n):
    return lambda x:x%n>0

## primes
def primes():
    yield 2
    it=odd_iter()
    while True:
        n=next(it)
        yield n
        it=filter(not_divisible(n),it)

# 打印1000以内的素数:
for n in primes():
    if n < 1000:
        print(n)
    else:
        break
