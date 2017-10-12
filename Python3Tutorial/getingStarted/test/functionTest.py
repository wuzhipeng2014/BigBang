#encoding:utf-8

def sumList(*list):
    sum=0
    for i in list:
        sum=sum+i
    return sum


print(sumList(1,2,3))

print(sumList())

