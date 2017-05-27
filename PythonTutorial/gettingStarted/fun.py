# coding:utf-8
# Filename:fun.py
import sys
print '命令行参数为:'
def sayHello():
    for i in (sys.argv):
        print i
        print '\n'

def printSysPath():
    print sys.path

version = '0.1'

sayHello()



