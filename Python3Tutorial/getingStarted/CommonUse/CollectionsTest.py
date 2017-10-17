#encoding:utf-8

from collections import namedtuple


##定义一个namedtuple
Point=namedtuple('Point',['x','y'])

p=Point(1,2)

print(p.x)
print(p.y )


## 定义一个namedtuple类型的circle
Circle=namedtuple('Circle',['x','y','z'])


##deque 双端队列
from collections import deque

q=deque(['a','b','c'])
q.append('x')

q.appendleft('y')

print(q)

q.pop()

print(q)

## defaultdict  包含默认值的dict

from collections import defaultdict

##创建defaultDict时传入一个函数
defaultDicttest=defaultdict(lambda: 'N/A')
print(defaultDicttest['key1'])
print(defaultDicttest['key2'])

## orderedDict 按插入顺序排序的dict,不是按key排序

from collections import OrderedDict
od=OrderedDict([('a',1),('b',2),('m',3)])
print(od)


## OrderedDict可以实现一个FIFO类型的dict,当容量超出限制时,先删除最早添加的key
class LastUpdatedOrderedDict(OrderedDict):

    def __init__(self, capacity):
        super(LastUpdatedOrderedDict, self).__init__()
        self._capacity = capacity

    def __setitem__(self, key, value):
        containsKey = 1 if key in self else 0
        if len(self) - containsKey >= self._capacity:
            last = self.popitem(last=False)
            print('remove:', last)
        if containsKey:
            del self[key]
            print('set:', (key, value))
        else:
            print('add:', (key, value))
        OrderedDict.__setitem__(self, key, value)




##Counter 计数器
from collections import Counter

c=Counter()


for ch in 'test':
    c[ch]=c[ch]+1

print(c)




