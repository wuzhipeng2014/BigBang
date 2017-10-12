#encoding:utf-8

## 生成器的有点是可以边循环,边计算, 节约内存使用

## 创建一个平方数生成器
g=(x*x for x in range(10))

for n in g:
    print(n)

## 斐波那契数列(函数中只要包含yield关键字就是一个生成器)
## generator的函数,在每次调用next()的时候执行,遇到yield语句返回
def fib(max):
    n,a,b=0,0,1
    while n <max:
        yield b
        a,b =b,a+b
        n =n +1
    return 'done'

f=fib(10)
for n in f :
    print(n)


## 获取generator生成器的返回值
g=fib(6)
while True:
    try:
        x=next(g)
        print('g:',x)
    except StopIteration as e:
        print('Generator return value is :', e.value)
        break






