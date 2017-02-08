# coding:utf-8
# Filename:try_catch.py

import time


class ShortInputExecption(Exception):
    '''自定义异常类'''
    def __init__(self, length, atleast):
        Exception.__init__(self)
        self.length = length
        self.atleast = atleast


try:
    s = raw_input('enter something --->')
    if (len(s) < 3):
        time.sleep(5)
        raise ShortInputExecption(len(s), 3)

    print s
except EOFError:
    print 'EOF of input'
except ShortInputExecption, x:
    print 'error occurred, expected length %d, your input length is %d' % (x.length, x.atleast)
else:
    print 'your input is right'
finally:
    print 'finally ,we must say something'

print 'done'
