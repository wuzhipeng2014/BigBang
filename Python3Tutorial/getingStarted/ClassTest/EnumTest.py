#encoding:utf-8

from enum import Enum, unique

Month=Enum('Month',('jan','feb','mar','apr','may'))

for name, member in Month.__members__.items():
    print(name,'=>',member,',',member.value)



class Weekday(Enum):
    sun=0
    mon=1
    tue=2
    wed=3
    thu=4
    fri=5
    sat=6

print(Weekday.sun)
print(Weekday(2))

print(Weekday.tue.value)

