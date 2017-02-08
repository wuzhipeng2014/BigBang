# coding:utf-8
# Filename:sysModuleTest.py


import sys


def readFile(filename):
    '''print file content to the standard output'''
    f = file(filename)
    while True:
        line = f.readline()
        if len(line) == 0:
            break
        print line
    f.close()

    # 获取输入参数


if len(sys.argv) < 2:  # 系统输入参数第一个为文件名本身
    print '没有指定参数'
    sys.exit()
if sys.argv[1].startswith('--'):
    option = sys.argv[1][2:]
    if option == 'version':
        print 'version1.2'
    elif option == 'help':
        print '输入version或help参数'
    else:
        print 'unknown option'
else:
    for filename in sys.argv[1:]:
        readFile(filename)
