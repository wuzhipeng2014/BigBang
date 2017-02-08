# coding:utf-8

class person:
    population = 0  # 定义类的全局变量

    def __init__(self, name, var1=3):  # 初始化方法
        self.name = name  # 定义类的局部变量
        self.__var1 = var1
        person.population += 1
        print "hello, %s" % name

    def tostring(self):
        print "%s, " % self.name

    __privateVar = -1  # 私有变量, 以__开头的变量名字约定为类的私有变量

    def __del__(self):
        person.population -= 1

    def printname(self):
        '''打印名字字符串'''  # 通过self.printname.__doc__来访问这个字符串
        print self.name


    def printPopulation(self):
        '''打印类对象的个数'''
        print person.population

    def printPrivateVar(self):
        '''打印私有变量'''
        print person.__privateVar

    def printlocalPrivateVar(self):
        print self.__var1
