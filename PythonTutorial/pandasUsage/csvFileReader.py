# encoding:utf-8

from pandas import DataFrame, read_csv
import matplotlib.pyplot as plt
import pandas as pd
import sys
import matplotlib
import time
import os

# %matplotlib inline


print ('Python version' + sys.version)
print ('Pandas version' + pd.__version__)
print('Matplotlib version' + matplotlib.__version__)

names = ['Bob', 'Jessica', 'Marry', 'John', 'Mel']
births = [968, 155, 77, 578, 973]

# 将两个list进行合并
BabyDataSet = list(zip(names, births))
print BabyDataSet

#将数据读入到dataframe
df=pd.DataFrame(data=BabyDataSet,columns=['Names','Births'])
print df

#将数据导出到csv文件
df.to_csv('births1880.csv',index=False,header=False)


#读取csv文件数据
location=r'births1880.csv'
df=pd.read_csv(location)
print '输出读取的csv数据'
print df

#指定读取的csv文件的列名
df_with_names=pd.read_csv(location,names=['Names','Births'])

print df_with_names

#删除创建的文件
os.remove(location)

# 对dataframe每列进行类型检查
print df.dtypes

# 检查指定列的数据类型
print df_with_names.Births.dtype

## 找出dataframe中births最大的Names
sorted=df_with_names.sort_values(['Births'],ascending=False)
print sorted.head(1)



maxValue=df_with_names['Births'].max()

maxName=df_with_names['Names'][df_with_names['Births']==df_with_names['Births'].max()].values
text=str(maxValue)+"-"+maxName




# Add text to graph
print '开始画图'
plt.figure(1)
# 将dataframe数据以图形的形式展示
df_with_names['Births'].plot()
plt.annotate(text, xy=(1, maxValue), xytext=(8, 0), xycoords=('axes fraction', 'data'), textcoords='offset points')
plt.show()

print("The most popular name")
print df_with_names[df_with_names['Births'] == df_with_names['Births'].max()]

#程序休眠10秒
# time.sleep(10)

print ('程序执行结束')











