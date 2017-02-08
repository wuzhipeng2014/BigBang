# coding:utf-8
# Filename: listOperationOptimization.py

from __future__ import division

'''列表相关的优化操作'''

list1 = [1, 2, 3, 4]
chtoeng = {'1': 'one', '2': 'two'}
# 简化列表元素操作,(根据条件用列表内指定的元素初始化其它列表)
list2 = [i * 2 for i in list1 if i == 3]
print list2
# 输出列表内容
print list1


# 函数传递可变参数
def getMeanAges(*args):
    sum = 0
    count = 0
    for i in args:
        sum += i
        count += 1
    try:
        result = sum / count
    except Exception, e:
        print e
        print '调用函数错误'
    return result


# 传递字典类型的参数  ????
def printList(**lists):
    for i in lists:
        print lists.get(i)


try:
    # 调用函数打印list
    print '调用函数打印list'
    printList(name='one', age='two')
    print '打印完毕'
    print getMeanAges(1, 2, 3, 4, 5, 6, 7)
    print 10 / 3
    print getMeanAges()
except Exception, e:  # 异常捕获,并打印错误信息
    print '调用函数错误'
    print e
