#encoding:utf-8

#该类继承自object
class Student(object):
    def __init__(self,name,score,status,sex):
        self.name=name    ##公有变量
        self.__score=score ##私有变量
        self._sex=sex  ##可以视为私有变量,但是可以直接访问
        self.__status__=status ##特殊变量,可以直接访问


    def set_score(self, score):
        if  0<=score<=100:
            self.__score=score
        else:
            raise ValueError('bad score')

    def print_score(self):
        print('%s: %s' % (self.name, self.__score))

    def get_grade(self):
        if  self.__score>=90:
            return 'A'
        elif self.__score>=60:
            return 'B'
        else:
            return 'C'

Bob=Student('Bob',23,1,'M')


print(Bob)

## 可以绑定任意类型的变量
Bob.name='Bob'
Bob.age=23

print(Bob.name,Bob.age)

Bob.print_score()
print(Bob.get_grade())


## python的类继承

# 1. 类似java的继承
# 2. 鸭子特点

'''对于静态语言（例如Java）来说，如果需要传入Animal类型，则传入的对象必须是Animal类型或者它的子类，否则，将无法调用run()方法。

对于Python这样的动态语言来说，则不一定需要传入Animal类型。我们只需要保证传入的对象有一个run()方法就可以了：'''

##获取对象信息
import types

def fun():
    print(fun.__name__)
#1. 获取变量类型
print(type(123)==type(456))
print(type(123)==int)
print(type(fun)==types.FunctionType) #判断函数类型
print(type(lambda x:x)==types.LambdaType) #判断lambda表达式类型



#2. 判断对象类型
s=Student('james',30,1,'M')

print(isinstance(s,Student))


print('输出对象所有属性和方法')
print(dir(s))

#判断对象是否有制定属性
if  hasattr(s,'name'):
    print('true')
else:
    print(False)

#获取对象指定属性
print(getattr(s,'name'))

#设置对象指定属性
setattr(s,'name','xMan')

## python 类中定义静态类型的变量

class person(object):
    name='personName'  #属于类的name属性(类属性)
    def __init__(self,n):
        self.name=n ## name归该类的所有实例对象所有(实例属性)


p=person('test')
print(p.name)
del p.name ## 删除实例的name属性
print(p.name) ## 再次调用s.name, 由于实例的name属性没有找到,类的name属性就显示出来









