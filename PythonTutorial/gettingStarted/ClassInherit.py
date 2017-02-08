# coding:utf-8
# Filename:ClassInherit.py

class baseClass:
    '''基类'''

    def __init__(self):
        self.name = 0  # 在此处定义的类的局部变量必须进行初始化,否则在类中的其他地方无法使用
        self.age = 1
        self.country = 2
        self.gender = 3

    def printPersonInfo(self):
        print "用户的基本信息: %s, %s, %s, %s" % (self.name, self.age, self.country, self.gender)


class Teacher(baseClass):   #继承自基类
    def __init__(self, salary):
        baseClass.__init__(self)
        self.salary = salary

    def getSalary(self):
        print self.salary


class student(baseClass):   #继承子基类
    def __init__(self, grade):
        baseClass.__init__(self)
        self.grade = grade

    def getGrade(self):
        print "学生的年级为:%s" %self.grade



