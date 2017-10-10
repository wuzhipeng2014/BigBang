#encoding:utf-8

## 列表生成器

print('生成一个list')

print(list(range(1,6)))

## 生成一个1-10内元素的平方组成的list
xSquare=[x*x for x in range(1,11)]

print(xSquare)

## for循环中还可以加上if判断,生成偶数的平方list

evenSquare=[x*x for x in range(1,11) if x%2==0]

print(evenSquare)

## for循环嵌套,生成全排列

permutationList=[m+n for m in 'ABC' for n in 'XYZ' if  m>n]
print(permutationList)


## 列出当前目录下的所有文件名和目录名
import os
print([d for d in os.listdir('.')])

## 遍历时通过两个变量生成list
d = {'x': 'A', 'y': 'B', 'z': 'C' }
items=[k+'='+v for k,v in d.items()]
print(items)






