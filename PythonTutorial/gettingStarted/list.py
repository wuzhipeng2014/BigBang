#!coding: utf-8
# Filename: list.py

shoplist = ['apple', 'mango', 'carrot', 'banana']

print 'shoplist.length= ', len(shoplist), '....'

for item in shoplist:
    print item
shoplist.append('grape')
shoplist.remove('apple')
del shoplist[0]
shoplist.sort()
print shoplist
