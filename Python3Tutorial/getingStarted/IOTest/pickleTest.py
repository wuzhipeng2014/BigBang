#encoding:utf-8

##序列化和反序列化
import pickle

### 序列化
f=open('dump.txt','wb')
d={'first':1,'second':2}
pickle.dump(d,f)
f.close()

### 反序列化
f=open('dump.txt','rb')
d=pickle.load(f)
f.close()

print(d)


## 利用json在不同的语言之间传递对象

import json
## 序列化
s=json.dumps(d)

print(s)

##反序列化
json_str='{"age": 20, "score": 88, "name": "Bob"}'
dct=json.loads(json_str)
print(dct)



## 类对象序列化和反序列化
class Student(object):
    def __init__(self,name,score,age):
        self.name=name
        self.score=score
        self.age=age

    ## 序列化函数
    def student2Dict(s):
        return {
            'name':s.name,
            'age':s.age,
            'score':s.score
        }
    ##反序列化函数
    def dict2Student(d):
        return Student(d['name'],d['score'],d['age'])

## 对象序列化
s=Student('Alex',98,21)

print(json.dumps(s,default=Student.student2Dict))

## 对象反序列化
d='{"age": 21, "name": "Alex", "score": 98}'
s=json.loads(d,object_hook=Student.dict2Student)

print(s.age)









