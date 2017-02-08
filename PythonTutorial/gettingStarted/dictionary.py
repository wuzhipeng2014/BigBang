#!coding:utf-8
# Filename:dictionary.py

chToEng={'1':'one',
         '2':'two',
         '3':'three',
         '4':'four'}

print chToEng['2']  #输出指定的键对应的值
print chToEng['3']
chToEng['5'] = 'five' #增加一个元素


print 'the length of chToEng is %s' % len(chToEng)

del chToEng['3'] #删除一个元素

print 'the length of chToEng is %s' % len(chToEng)

for ch, eng in chToEng.items():  #输出字典中的键值对
    print '%s:%s' % (ch, eng)

if  '3' in chToEng:   #判断指定的键值是否在字典中的方法
    print '3:%s' % chToEng['3']
else:
    print '3 is not in chToEng'





