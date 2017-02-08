# coding:utf-8
# Filename:fileTest.py
import cPickle as p

poem = '''下雪了,好冷
要放假了...
期待ing...'''

f = file("poem.txt", 'a')  # 追加的方式打开文件,还可以指定其他的模式:r|w|a
f.write(poem)
f.close()
f = file("poem.txt")
while True:
    line = f.readline()
    if len(line) == 0:
        break
    print line

f.close()

# ------------------python持久化存储对象 pickle和cpickle-----------------

shoplistFile = 'shoplist.data'

# 使用cPickle实现对象存储
shoplist = ['apple', 'mango', 'carrot']
f = file(shoplistFile, 'w')
p.dump(shoplist, f)
f.close()

# 使用cPickle实现对象读取
f = file(shoplistFile)
storedlist = p.load(f)
print storedlist

# cpickle加载的对象保持原有的类型
print storedlist[2]


