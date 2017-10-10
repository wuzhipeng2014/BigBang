#encoding:utf-8

## dict (map)

ageMap={'james':32,'Bob':28,'Tom':30}

print(ageMap['james'])

ageMap['Jack']=26

print(ageMap['Jack'])

# 判断dict中是否存在指定的key对应的value
if  'James' in ageMap:
    print(ageMap.get('james'))
else:
    print('指定姓名不存在')

# 如果dict中指定的key不存在,可以指定默认返回值
Jam= ageMap.get('Jam',-1)
print(Jam)

# 删除dic中指定元素
ageMap.pop('james')

print(ageMap)


## set
nameSet=set([1,2,3])
print(nameSet)

# set内添加元素
nameSet.add(4)
nameSet.add(5)
print(nameSet)

# set删除元素
nameSet.remove(3)
print(nameSet)

# set进行交集 并集
ageSet=set([5,6,7])
print(nameSet|ageSet)

print(nameSet&ageSet)








