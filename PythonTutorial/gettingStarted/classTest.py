# coding:utf-8

import classObject


p = classObject.person("name")

p1=classObject.person("name1")
p2=classObject.person("name2")
p3=classObject.person("name3")

p1.printname()
p1.printPopulation()

p1.__del__()

p2.printPopulation()
p3.printPopulation()


print p2.printname.__doc__


print  p2.population

print '打印私有变量'
p2.printPrivateVar()


print '打印局部私有变量'
p2.printlocalPrivateVar()


print p2.name   #访问类的局部变量









p.tostring()




