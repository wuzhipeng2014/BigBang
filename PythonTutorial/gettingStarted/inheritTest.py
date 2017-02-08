# coding:utf-8
# Filename:inheritTest.py

import ClassInherit

p = ClassInherit.baseClass()

p.printPersonInfo()

stu = ClassInherit.student(3)

tea = ClassInherit.Teacher(1000)

print "输出学生的年级"
stu.getGrade()

print "输出教师的工资"
tea.getSalary()
