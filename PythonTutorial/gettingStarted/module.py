#coding: utf-8
#Filename: module.py
import fun

print fun.printSysPath()

print '调用的函数的版本为:'
print fun.version


identifier= dir(fun)
print identifier

print fun.__doc__