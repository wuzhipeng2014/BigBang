# coding:utf-8
# Filename:PersonalContacts.py

import cPickle as cPickle
import os
import sys


class person:
    def __init__(self):
        print '创建person对象'

    def __init__(self, name, address, corp, telephone):
        self.name = name
        self.address = address
        self.telephone = telephone
        self.corp = corp

    def __repr__(self):
        return '%s, %s, %s, %s' % (self.name, self.address, self.corp, self.telephone)

    # 存储联系人
    def writeFile(self, personList):
        if not os.path.isfile('/home/zhipengwu/secureCRT/contacts/contacts.txt'):
            f = file('/home/zhipengwu/secureCRT/contacts/contacts.txt', 'a')
        else:
            f = file('/home/zhipengwu/secureCRT/contacts/contacts.txt', 'a')
        try:
            cPickle.dump(personList, f)
            # for p in personList:
            #     f.write(repr(p))
        except Exception, e:
            print '存储联系人失败, %s' % e.message
        finally:
            f.close()
        print '存储联系人成功'

    # 读取联系人
    def readFile(self):
        try:
            f = file('/home/zhipengwu/secureCRT/contacts/contacts.txt', 'r')
        except Exception, e:
            print '读文件失败,%s' % e.message
            return
        personList = []
        while True:
            try:
                tmpPersonList = cPickle.load(f)
                for p in tmpPersonList:
                    personList.append(p)
            except EOFError, e:
                print '读文件完毕'
                break
            except Exception, e:
                print '读取联系人出错,%s' % e.message
                break
        f.close()
        return personList

    # 获取联系人信息
    def getContact(self, name):
        personList = self.readFile()
        for p in personList:
            if p.name == name:
                return p

    # 获取所有联系人
    def getAllContacts(self):
        personList=self.readFile()
        return personList


    # 添加联系人
    def addContact(self):
        personList = self.readFile()
        if not personList:
            personList = []
        while True:
            name = raw_input('输入联系人姓名:')
            address = raw_input('输入联系人地址:')
            corp = raw_input('输入联系人公司:')
            telephone = raw_input('输入联系人电话')
            p = person(name, address, corp, telephone)
            personList.append(p)
            condition = raw_input('是否继续输入,Y or N')
            if condition == 'N':
                self.writeFile(personList)
                break


p = person("name", "a", "qunar", "188")



while True:
    option = raw_input('输入你想进行的操作: 1.添加联系人 2. 查询联系人 3. 查询所有联系人 q. 退出 ')
    if option == '1':
        p.addContact()
        continue
    if option == '2':
        name = raw_input('你想查询的联系人名字')
        print p.getContact(name)
        continue
    if option == '3':
        personList=p.getAllContacts()
        for p in personList:
            print p
        break
    if option == 'q':
        break
    else:
        print "error, 请输入正确的选项"

print os.linesep # 打印系统的行分隔符
