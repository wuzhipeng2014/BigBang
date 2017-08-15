# encoding:utf-8

## 使用svm实现手写数字识别


import pandas as pd
import matplotlib.pyplot as plt, matplotlib.image as mping
from sklearn.model_selection import train_test_split
from sklearn import svm

# step1. 加载数据
print '开始加载数据'

labeled_images = pd.read_csv('../resource/train.csv')
images = labeled_images.iloc[0:5000, 1:]
labels = labeled_images.iloc[0:5000, :1]

train_images, test_images, train_labels, test_labels = train_test_split(images, labels, train_size=0.8, random_state=0)

print'训练数据加载&分割完成'

i = 1

# img = train_images.iloc[i].as_matrix()
# img = img.reshape((28, 28))
# print '开始展示原始图像'
# plt.imshow(img, cmap='gray')
# plt.title(train_images.iloc[i, 0])
#
# ## 显示图像的柱状图
# plt.hist(train_images.iloc[i])
#
# print '图像柱状图展示完毕'
# ## 使用svm模型拟合图像
# clf = svm.SVC()
# clf.fit(train_images, train_labels.values.ravel())
# print clf.score(test_images, test_labels)
# print '程序运行结束'

# 将训练图像和测试图像分别转换为黑白图像
test_images[test_images > 0] = 1
train_images[train_images > 0] = 1
img = train_images.iloc[i].as_matrix().reshape((28, 28))
print '展示二值图像'
plt.imshow(img, cmap='binary')
# plt.title(train_labels.illoc[i])

plt.hist(train_images.iloc[i])
print '图像显示完毕'

## 使用svm模型拟合图像
clf = svm.SVC()
clf.fit(train_images, train_labels.values.ravel())
print '输出测试集合上的预测准确率'
print clf.score(test_images, test_labels)

## 对测试数据进行预测
test_data = pd.read_csv('../resource/test.csv')
test_data[test_data > 0] = 1
results = clf.predict(test_data[0:5000])
##将预测结果输出为csv格式
df = pd.DataFrame(results)
df.index.name = 'ImageId'
df.index += 1
df.columns = ['Label']
df.to_csv('result.csv', header=True)

print '程序运行结束'
