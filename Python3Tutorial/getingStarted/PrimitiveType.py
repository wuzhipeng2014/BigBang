# coding:utf-8


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


test1='{name}abc{age}'.format(name='妹妹',age=3)

print(test1 )













