# endcoding:utf-8

## titanic 幸存者 预测


import pandas as pd
import numpy as np

# 读入训练数据
train = pd.read_csv('../../resource/titanic/train.csv')
test = pd.read_csv('../../resource/titanic/test.csv')

all_data = pd.concat((train.loc[:, 'Pclass':'Embarked'], test.loc[:, 'Pclass':'Embarked']))

## 将分类特征数字化表示
all_data = pd.get_dummies(all_data)

# 对空的特征值进行中值填充
all_data = all_data.fillna(all_data.mean())

def rmse_cv(model):
    rmse = np.sqrt(-cross_val_score(model, X_train, y, scoring="neg_mean_squared_error", cv=5))
    return (rmse)

# 为sklearn创建矩阵
X_train = all_data[:train.shape[0]]
X_test = all_data[train.shape[0]:]
y = train.Survived
from sklearn.linear_model import Ridge, RidgeCV, ElasticNet, LassoCV, LassoLarsCV
from sklearn.model_selection import cross_val_score
## lasso 模型 α||w||_1 1范数

model_lasso = LassoCV(alphas=[0.05, 0.1, 0.3, 1, 3, 5, 10, 15, 30, 50, 75]).fit(X_train, y)


model_ridge = Ridge(alpha=[0.01]).fit(X_train, y)




print "lasso rmse: "


print rmse_cv(model_lasso).mean()

print rmse_cv(model_ridge).mean()


