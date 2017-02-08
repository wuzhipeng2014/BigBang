# coding:utf-8
# Filename:osModuleTest.py


import os

# 获取当前平台的名字
print os.name

# 获取当前工作目录
print os.getcwd()

# 获取制定目录下的所有文件和目录名

print os.listdir('/home/zhipengwu/secureCRT/desetination_7')

# 删除一个文件
try:
    fileTobeRemoved='/home/q/zhipeng.wu/work/tseet.txt'
    os.remove(fileTobeRemoved)
except Exception:
    print '删除文件失败', fileTobeRemoved

# 运行shell命令
os.system('pwd')

# 获取当前平台的行终止符号
print os.linesep

# 返回一个路径的目录和文件名
os.path.split('/home/q/zhipeng.wu/work/tmp.txt')

# 判断一个路径是文件还是目录
os.path.isdir('/home/q/zhipeng.wu')

os.path.isfile('/home/q/zhipeng.wu/test.txt')
