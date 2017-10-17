#encoding:utf-8

import time,threading

# 新线程执行的代码:
def loop():
    print('thread %s is running...' % threading.current_thread().name)
    n = 0
    while n < 5:
        n = n + 1
        print('thread %s >>> %s' % (threading.current_thread().name, n))
        time.sleep(1)
    print('thread %s ended.' % threading.current_thread().name)

print('thead %s is runing...' %threading.currentThread().name)

## 创建子线程
t=threading.Thread(target=loop,name='subThread')

t.start()
t.join()

print('thread %s end.' %threading.current_thread().name)

