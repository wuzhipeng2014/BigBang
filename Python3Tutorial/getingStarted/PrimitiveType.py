# coding:utf-8


## 字符串类型
#无需转义,直接打印字符串
print(r'\\\\\\tttt\\\\')

# 打印普通字符串
print('abc"')
print("abc'")
print("abc\"")
print('abc\'')

#打印多行字符串,包括换行符
print('''line1
line2
line3''')

## boolean 类型

print(3>2)

if not 1>2:
    print("1>2 is false")

# 算法运算符
print(10/3)

print(10//3)

print(10%3)

# 编码问题
# 计算机中都是以unicode编码的方式处理字符,Unicode一般是两个字节的长度
# utf-8是一种变长的编码方式,较长使用的字符如a-z是以一个字节进行编码的,但是汉字可能以3个字节进行编码.比较适合进行网络传输


print(ord('c'))

print(chr(99))

## python中的字符串在内存中以unicode表示, 如果要在网络上进行传输或者保存到磁盘上,就需要把str转换为以字节为单位的bytes
x=b'abc'
print(x)

bytes='中国'.encode('utf-8')

print(bytes)

print(ord("中"))

## 字符串输出进行格式化
print('abc%s' %('妹妹'))

## 字符串中包含特征符号
print('pass rate %d %%' %(90))

test1='{name}abc{age}'.format(name='妹妹',age=3)
print(test1 )


## list数据类型
namelist=['name','age','time']
print(namelist)

## 获取list的最后一个字符
print(namelist[-1])
## 获取list的倒数第二个字符
print(namelist[-2])

## 把元素插入到list中的指定位置
namelist.insert(5,'hometown')
namelist.insert(2,'river')
print(namelist)
## 删除list中指定位置的元素
print('pop namelist')
print(namelist.pop(1))

print(namelist)

## 修改list中指定位置上的元素
namelist[0]="first"
print(namelist)

## list中元素的类型可以不相同,同时list中的元素可以为一个list
mixList=[1,2,3,['a','b','c'],'more']
print(mixList)
print(mixList[3][0])

## tuple   (tuple与list非常相似,但是tuple一旦初始化就不能修改),增强了代码安全性
classmates=("michel",'bob','tracy')
print(classmates)

##需要注意的是定义只有一个元素的tuple时,需要加逗号(,)
singleTuple=(1,)
print(singleTuple)

## 注意这种形式与上中形式的差别
singleTuple=(1)
print(singleTuple)

## tuple内的元素不可变指的是指向的变量地址不能改变,但是指向地址内的内容是可以改变的

t = ('a', 'b', ['A', 'B'])
t[2][0]='X'
t[2][1]="Y"

print(t)




















