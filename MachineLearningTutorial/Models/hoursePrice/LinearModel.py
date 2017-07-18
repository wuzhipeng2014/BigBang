# encoding:utf-8

## 线性模型实现房价预测

import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib
import matplotlib.pyplot as plt
from scipy.stats import skew
from scipy.stats.stats import pearsonr

train = pd.read_csv("../../resource/hoursePrice/train.csv")
test = pd.read_csv("../../resource/hoursePrice/test.csv")
# print train.head()

## 将两个字符串连接在一起
# all_data = pd.concat((train.loc[:'MSSubClass':'SaleCondition'], test.loc[:, 'MSSubClass':'SaleCondition']))
matplotlib.rcParams['figure.figsize'] = (12.0, 6.0)
prices = pd.DataFrame({"price": train["SalePrice"], "log(price+1)":np.log1p(train["SalePrice"])})

prices.hist()
plt.show()

print "done"
