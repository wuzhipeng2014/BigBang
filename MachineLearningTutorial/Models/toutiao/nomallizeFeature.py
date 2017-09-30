#encoding:utf-8

import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib
import matplotlib.pyplot as plt
from scipy.stats import skew

all_data = pd.read_csv("/home/zhipengwu/secureCRT/toutiao_test.csv")
# all_data = pd.read_csv("/home/zhipengwu/secureCRT/hotel_test_20170813.libsvm.csv")
numeric_feats = all_data.dtypes[all_data.dtypes != "object"].index
skewed_feats = all_data[numeric_feats].apply(lambda x: skew(x.dropna()))
skewed_feats = skewed_feats[skewed_feats > 0.75]
print '偏差较大的列有:　\n'
print skewed_feats
skewed_feats = skewed_feats.index
# all_data[skewed_feats] = np.log1p(all_data[skewed_feats])

featureCloum='shiftCitysOnWorkingDay0'
maxActiveRadiusOnWorkingDay0 =  pd.DataFrame({"log50":(np.log1p(all_data[featureCloum]*50)),"log10":(np.log1p(all_data[featureCloum]*10)),"log": (np.log1p(all_data[featureCloum])),"origin":all_data[featureCloum]})
maxActiveRadiusOnWorkingDay0.hist()
plt.show()

