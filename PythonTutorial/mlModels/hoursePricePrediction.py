# encoding:utf-8

# 使用正则线性模型预测房价

import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib
import matplotlib.pyplot as plt
from scipy.stats import skew
from scipy.stats.stats import pearsonr

import xgboost as xgb

# 读入训练数据
train = pd.read_csv('../resource/train.csv')
test = pd.read_csv('../resource/test.csv')
print train.head()

all_data = pd.concat((train.loc[:, 'MSSubClass':'SaleCondition'], test.loc[:, 'MSSubClass':'SaleCondition']))

## 训练数据处理

# 1. 对数字特征取对数使特征更具一般性
# 2. 为描述性的分类特征创建变量
# 3. 用缺失的特征值用对应列的均值进行填充

## 显示房价的柱状图
matplotlib.rcParams['figure.figsize'] = (12.0, 6.0)
# 创建dataframe
prices = pd.DataFrame({"price": train["SalePrice"], "log(price+1)": np.log1p(train["SalePrice"])})
prices.hist()
plt.show()
print '房价柱状图显示完成'

##将房价数据进行对数处理
train["SalePrice"] = np.log1p(train["SalePrice"])

##将所有偏差较大的数字特征进行对数处理
numeric_feats = all_data.dtypes[all_data.dtypes != "object"].index

# 计算skewess,并对数字特征进行对数处理
skewed_feats = train[numeric_feats].apply(lambda x: skew(x.dropna()))
skewed_feats = skewed_feats[skewed_feats > 0.75]
skewed_feats = skewed_feats.index
all_data[skewed_feats] = np.log1p(all_data[skewed_feats])

## 将分类特征数字化表示
all_data = pd.get_dummies(all_data)

# 对空的特征值进行中值填充
all_data = all_data.fillna(all_data.mean())

# 为sklearn创建矩阵
X_train = all_data[:train.shape[0]]
X_test = all_data[train.shape[0]:]
y = train.SalePrice

##使用正则化线性回归拟合数据, 同时定义了一个函数返回交叉验证的rmse 误差,用来对模型进行评估并选出表现最好的参数
##------------------------------------------------------------------------------------
# 使用正则化线性模型拟合数据
##------------------------------------------------------------------------------------
from sklearn.linear_model import Ridge, RidgeCV, ElasticNet, LassoCV, LassoLarsCV
from sklearn.model_selection import cross_val_score


def rmse_cv(model):
    rmse = np.sqrt(-cross_val_score(model, X_train, y, scoring="neg_mean_squared_error", cv=5))
    return (rmse)


# Ridge模型中需要调节的参数为alpha-用来度量模型的适应性
# 模型 α||w||_2 2范数
model_ridge = Ridge()

alphas = [0.05, 0.1, 0.3, 1, 3, 5, 10, 15, 30, 50, 75]
cv_ridge = [rmse_cv(Ridge(alpha=alpha)).mean()
            for alpha in alphas]
cv_ridge = pd.Series(cv_ridge, index=alphas)
cv_ridge.plot(title="validation-just do it")
plt.xlabel("alpha")
plt.ylabel("rmse")
plt.show()

print '最小的rmse为:'
print cv_ridge.min()

## lasso 模型 α||w||_1 1范数
model_lasso = LassoCV(alphas=[1, 0.1, 0.001, 0.0005]).fit(X_train, y)
print "lasso rmse: "
print rmse_cv(model_lasso).mean()

coef = pd.Series(model_lasso.coef_, index=X_train.columns)

print(
"Lasso picked " + str(sum(coef != 0)) + " variables and eliminated the other " + str(sum(coef == 0)) + " variables")

imp_coef = pd.concat([coef.sort_values().head(10), coef.sort_values().tail(10)])

imp_coef.plot(kind="barh")
plt.title("Coefficients in the Lasso Model")
plt.show()

# 模型预测结果展示
preds = pd.DataFrame({"preds": model_lasso.predict(X_train), "true": y})
preds["residuals"] = preds["true"] - preds["preds"]
preds.plot(x="preds", y="residuals", kind="scatter")
plt.show()

## 显示估计误差的分布
diff = preds["true"] - preds["preds"]
diff.hist()
plt.show()





