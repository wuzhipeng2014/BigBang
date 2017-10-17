#encoding:utf-8

from datetime import  datetime

##输出当前时间
now=datetime.now()

print(now)

##创建datetime类型时间
dt=datetime(2017,10,17,10,45)

print(dt)


## 标准时间,格林尼治时间, 与时区无关
timestamp=0  ##1970-1-1 00:00:00 UTC+0:00

##对应的北京时间为
# timestamp = 0 = 1970-1-1 08:00:00 UTC+8:00

'''
可见timestamp的值与时区毫无关系，因为timestamp一旦确定，其UTC时间就确定了，转换到任意时区的时间也是完全确定的，这就是为什么计算机存储的当前时间是以timestamp表示的，因为全球各地的计算机在任意时刻的timestamp都是完全相同的（假定时间已校准）。
'''

## datetime转换为timestamp
dt=datetime(2017,10,17,10,45)
print(dt.timestamp())

##timestamp转换为datetime
t=1508208300.0

##转换为本地时间
print('UTC+8:00:',datetime.fromtimestamp(t))

##转换为UTC时间
print('UTC+0:00:', datetime.utcfromtimestamp(t))

## str转换为datetime
timestr='2017-10-17 10:59:36:123'

formatedTime=datetime.strptime(timestr,'%Y-%m-%d %H:%M:%S:%f')

print(formatedTime)


##datetime转换为str
now=datetime.now()
print(now.strftime('%Y-%m-%d'))

## datetime加减
from datetime import timedelta
now=datetime.now()

print(now+timedelta(days=1))


## 本地时间转换为UTC时间
from datetime import timezone
print(datetime.now().tzinfo)  ##默认时区信息为空

# 强行制定时区信息
tz_utc_8= timezone(timedelta(hours=8))
now=datetime.now()

dt=now.replace(tzinfo=tz_utc_8)

print(now)
print(dt)

## 输出当前时区
utc_dt=datetime.utcnow().replace(tzinfo=timezone.utc)
print('UTC时间: ',utc_dt)

##转换为北京时间
bj_dt=utc_dt.astimezone(timezone(timedelta(hours=8)))
print('北京时间: ',bj_dt)

##转换为东京时间
tokyo_dt=utc_dt.astimezone(timezone(timedelta(hours=9)))

print('东京时间',tokyo_dt)

##北京时间转换为东京时间

tokyo_dt2=bj_dt.astimezone(timezone(timedelta(hours=9)))

print('北京时间转东京时间:',tokyo_dt2)













