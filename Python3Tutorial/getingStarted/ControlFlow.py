# coding:utf-8


## if else语句
age=10
if  age>18:
    print('成年人')
elif age<3:
    print("小屁孩")
elif age==10:
    print("好孩子")
else:
    print("熊孩子")



## 从控制台输入参数
# birth=input('birth: ')
birth=2000
if  int(birth)<2000:
    print('90后')
else:
    print('00后')


## for 循环

print("获奖人员姓名:")
names=['james','bob','tom']
for name in names:
    print(name)


numbers=range(101)
sum=0
for number in numbers:
    sum=sum+number

print(sum)



## while 循环
sum=0
n=99
while n>0:
    sum=sum+n
    n=n-2
print(sum)













