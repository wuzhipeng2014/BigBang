# coding:utf-8
# Filename:exec_eval_test.py

import os

# 执行存储在文件中的python语句

printDir = 'print os.getcwd()'

exec printDir  # 执行字符串中的python命令

print 2 * 3

os.getcwd()

mylist = ['one', 'two']

try:
    assert len(mylist) > 3
except Exception, e:
    print e.__class__
    print '打印异常信息'

print '.........'

# eval 执行Python字符串
tmp = eval('2-1')
print tmp
