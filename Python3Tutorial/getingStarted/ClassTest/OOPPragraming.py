#encoding:utf-8

##面向对象高级编程

from types import MethodType

class student(object):
    pass

s=student()
s.name='Michael'
s.age=25

def speak(self,word):
    print('%s 说: %s' %( speak.__name__, word))


#给实例绑定一个方法
s.speak=MethodType(speak,s)

s.speak('hello')

#给类绑定一个方法,所有类的实例对象都可以调用
student.say=speak

p=student()
p.say('monday')



##使用__slots__ ,限制可以添加到类的属性
class Coder(object):
    __slots__=('work','sleep')

c=Coder()
c.work=10
c.sleep=8
# c.game=4 #收到__slots__的限制,无法添加


# Fellow继承子Coder,但是Fellow不受Coder类中__slot__方法中定义属性的限制
class Fellow(Coder):
    def __init__(self,office):
        self.__office=office

    ## getter
    @property
    def office(self):
         return self.__office
    ##setter
    @office.setter
    def office(self, value):
        self.__office=value



f=Fellow('15L')
f.office='17L'

print(f.office)
f.game=2








