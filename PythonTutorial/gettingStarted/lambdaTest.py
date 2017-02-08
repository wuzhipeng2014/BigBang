# coding:utf-8
# Filename:lambdaTest.py

def make_repeater(n):
    return lambda s: s*n

# 创建正则表达式
twice=make_repeater(2)

print twice('one')
print twice(5)

