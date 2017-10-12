#encoding:utf-8
import functools
import time
## 在函数运行期间增强函数功能, 不修改函数定义的方式 称为装饰器


##定义一个装饰器,调用now函数之前打印日志
def log(func):
    @functools.wraps(func)  #用func函数的名字覆盖wrapper函数的名字
    def wrapper(*args, **kw):
        print('call %s():'% func.__name__) ## 调用函数之前打日志
        func(*args,**kw)  ## 调用函数
        print('end call')  ##调用函数之后打日志
        return
    return wrapper

@log
def now():
    print('2017-10-11')

f=now

f()
print('函数名字')
print(f.__name__)


## 给装饰器传递参数####

def logWithArgs(name='hell0'):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args,**kw):
            ## 在函数运行期间增强函数功能, 不修改函数定义的方式 称为装饰器
            print('%s %s()' %(name,func.__name__))
            func(*args,**kw)
            print('%s 函数执行完成' %func.__name__)
            return func
        return wrapper
    return decorator


@logWithArgs()
def nowArgs():
    print(time.strftime('%Y-%m-%d',time.localtime(time.time())))

nowArgs()
print(nowArgs.__name__)














