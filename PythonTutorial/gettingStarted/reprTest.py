# coding:utf-8
# Filename:reprTest.py

'''repr函数用于获取对象的规范字符串表示'''

i = []
i.append('5')
i.append('test1')


class student:
    # 类的初始化函数
    def __init__(self, name, age):
        self.name = name
        self.age = age
    # 定义对象的规范字符串表示
    def __repr__(self):
        return 'name: %s, age: %s' % (self.name, self.age)


print `i`  # 对象的规范字符串表示
print repr(i)  # 对象的规范字符串表示

p = student('zhipeng.wu', 23)

print p.__repr__()
