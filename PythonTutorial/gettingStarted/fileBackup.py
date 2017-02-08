#!coding:utf-8
# Filename:fileBackup.py

import os
import time
import sys

print time.strftime('%Y-%m-%d %H:%M:%S')

# source = ['/home/zhipengwu/data/sr.sh', '/home/zhipengwu/data/str.sh']
source = []

leng = len(sys.argv)

for item in sys.argv[2:leng + 1]:
    source.append(item)

target = sys.argv[1]


# targetDir = '/home/zhipengwu/data/'

# target = targetDir + time.strftime('%Y-%m-%d')

timeNow = time.strftime('%H%M%S')

if not os.path.exists(target):  # 判断目录是否存在
    os.mkdir(target)  # 创建目录

comment = raw_input('enter a comment --> ')

target = target + os.sep + timeNow + '_' + comment.replace(' ', '_') + '.zip'  # os.sep根据操作系统给出目录的分隔符,增强程序的移植性

print 'target:  %s' % target
print 'source: %s' % ' '.join(source)  # 用空格连接字符串

zipCommand = "zip '%s' %s" % (target, ' '.join(source))
if os.system(zipCommand) == 0:  # 执行命令字符串
    print 'successfully backup to ', target
else:
    print 'fail to backup'
