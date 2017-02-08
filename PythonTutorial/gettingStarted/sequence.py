#!coding:utf-8
#Filename: sequence.py
shoplist=['apple', 'pear', 'mango', 'carrot', 'banana']

print 'item 0 is %s ' % (shoplist[0])  # 序列通过索引访问

print 'item 1 to 3 is ', shoplist[1:3]  # 通过切片访问shoplist[startIndex,endIndex], 不返回endIndex

print 'all items are: ', shoplist[:]   # 通过切片访问所有

name='swaroop'

print 'characters 1 to 3 is', name[1:3]  # 字符串同样可以通过切片访问

print 'characters start to end is ', name[:]  # 通过切片访问字符串中的所有字符






