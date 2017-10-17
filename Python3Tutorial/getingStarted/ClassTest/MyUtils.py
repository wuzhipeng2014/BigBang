#encoding:utf-8

import time
import datetime



def formatedDate(timestr=''):
    if(timestr.strip()==''):
        return time.strftime('%Y-%m-%d  %H:%M:%S',time.localtime(time.time()))
    else:
        return datetime.datetime.strptime(timestr,'%Y-%m-%d')



if __name__=='__main__':
    print('当前时间: ',datetime.datetime.now())

    print('call formatedDate(): ',formatedDate())

    print('call formatedDate("2017-10-15"): ',formatedDate('2017-10-15'))



    start_date = datetime.datetime.strptime("2016-06-07", "%Y-%m-%d")

    print(start_date)






