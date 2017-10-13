#encoding:utf-8

##多重继承

class Person(object):
    def __init__(self,name,age):
        self.__name=name
        self.__age=age
    @property
    def name(self):
        return __name__

    @name.setter
    def name(self,value):
        self.__name=value


class Student(object):
    def __init__(self,grade):
        self.__grade=grade


##多重继承
class Graduate(Person,Student):
    def __init__(self,gpa):
        self.__token=gpa



g=Graduate(5)

print(g.__token)