#encoding:utf-8

import functools
##偏函数:
##当函数的参数太多需要简化时, 可以使用偏函数固定函数的部分参数

int2=functools.partial(int,base=2)

## 将字符串转化为2进制的数(int函数的base默认等于10)
print(int2('110'))



## 求最大值
max2=functools.partial(max,10)

result=max2(5,6,7)
print(result)







