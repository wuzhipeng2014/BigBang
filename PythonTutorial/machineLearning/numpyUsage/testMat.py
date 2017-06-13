
#coding:utf-8
# ------------------------------------------------------------
# 简介 : 机器学习基础 numpy
#
# 更新 : 2015年1月7日@
# ------------------------------------------------------------
from numpy import random
from numpy.matrixlib.defmatrix import mat
from numpy.lib.twodim_base import eye
#4*4随机数组
random.rand(4,4)
#数组转矩阵
randmat=mat(random.rand(4,4))
print(randmat)
#.I专为逆矩阵
print(randmat.I)
randmatv=randmat.I
#矩阵和逆矩阵相乘，对角为1 其他为0   -eye(4)为单位矩阵 相减后看到有误差
print(randmat*randmatv-eye(4))