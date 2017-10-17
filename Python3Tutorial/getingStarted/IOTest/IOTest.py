#encoding:utf-8


## 读取文件内容并输出

try:
    f=open('../../resource/testInput.txt')
    print(f.read())
finally:
    if f:
        f.close()

##使用with实现文件读取,读取完毕后无需关闭文件
line='begin'
with open('../../resource/testInput.txt') as f:
    while line!='':
        try:
            line=f.readline()
        except Exception as e:
            print('读取文件结束')
            break
        finally:
            print(line)

## 读取二进制文件,'rb'方式打开,打开文本文件默认是utf-8编码
f=open('../../resource/Image.png','rb')

print(f.read())
f.close()

## 读取非utf-8编码的文本文件,需要制定open函数的encoding参数
f=open('../../resource/countries.txt',encoding='utf-8')

print(f.read())

f.close()

## 在文本文件中可能包含非法编码的字符,遇到这种情况,可以制定open函数的errors参数

f=open('../../resource/countries.txt',encoding='utf-8',errors='ignore')


##写文件

with open('../../resource/output.txt','w') as f:
    f.write('test output')

##写文件时制定编码方式
with open('../../resource/output.txt','w',encoding='gbk') as f:
    f.write('test output完美')


## StringIO(将字符串写入内存)
from io import StringIO
f=StringIO()
f.write("test stringio")
f.write(" ")
f.write("中文")

print(f.getvalue())

f=StringIO('Hello!\nHi!\nGoodbye!')
while True:
    s=f.readline()
    if  s=='':
        break
    print(s)


##BytesIO (向内存中写入BytesIO)
from io import BytesIO
f=BytesIO()
f.write('中文'.encode('utf-8'))

print(f.getvalue())

## 操作文件和目录

##获取系统详细信息
import os
print(os.uname())

## 打印系统名字
print(os.name)

## 环境变量:　在操作系统中定义的环境变量，全部保存在os.environ这个变量中
print(os.environ)

print(os.environ.get('PATH'))

print(os.environ.get('x','defualt'))

## 操作文件和目录
## 查看当前目录的绝对路径
print( '当前绝对路径为:　',os.path.abspath('.'))

## 在某个目录下创建一个新目录，首先把新目录的路径表示出来
newDir=os.path.join('/home/zhipengwu/secureCRT','test')

os.mkdir(newDir)
os.rmdir(newDir)

## 调用函数拆分文件路径
basedir, name=os.path.split('/home/zhipengwu/secureCRT/test.txt')

print(basedir)
print(name)

## 文件重命名

open('/home/zhipengwu/secureCRT/test.txt','w')
os.rename('/home/zhipengwu/secureCRT/test.txt','/home/zhipengwu/secureCRT/test.py')

##删除文件
os.remove('/home/zhipengwu/secureCRT/test.py')


##列出目录下的所有文件夹
listDir=os.listdir('/home/zhipengwu/secureCRT/train')
print(listDir)
for x in listDir:
    if os.path.isdir(os.path.join('/home/zhipengwu/secureCRT/train',x)):
        print(x)


## 列出制定目录的所有gz文件

gzlist= [x for x in os.listdir('/home/zhipengwu/secureCRT/train') if os.path.isfile(os.path.join('/home/zhipengwu/secureCRT/train',x)) and os.path.splitext(x)[1]=='.gz']


print(gzlist)






