#!coding:utf-8
# Filename:objectAndReference.py

shoplist = ['apple', 'mango', 'carrot', 'banana']

referShoplist=shoplist  # 给变量增加一个别名
copyShoplist=shoplist[:]  # 拷贝变量内容

print shoplist
print  referShoplist


del shoplist[0]

print shoplist
print referShoplist
print copyShoplist
print 'copyShoplist is :', copyShoplist[:]









