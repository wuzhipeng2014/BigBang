#! coding:utf-8
# Filename:str.py

name = 'swaroop'
if  name.startswith('swa'):
    print 'yes'

if 'a' in name:  #
    print 'a'

if name.find('oo') != -1:  #找不到返回-1
    print 'oo'

delemiter = '__*__'
list = ['one', 'two', 'three']
print delemiter.join(list)

if name.__contains__('ar') == 1:
    print 'ar'

print name.__contains__('ar')
