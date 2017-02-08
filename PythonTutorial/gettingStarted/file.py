# coding:utf-8
# Filename:File.py


line = '''this is a input line
line2
line3
line4
'''

f = file('/home/zhipengwu/work/tmp/fileout.txt', 'w')
print '输出字符串到文件'

f.write(line)
f.close()  # 写文件完成后要关闭输入流,否则此文件不能被其他文件对象使用

print '写入完成'

fin = file('/home/zhipengwu/work/tmp/fileout.txt')

while True:
    s = fin.readline()
    if len(s) == 0:
        break
    print s
fin.close()  # 读文件完毕关闭文件

print '读文件完毕'
