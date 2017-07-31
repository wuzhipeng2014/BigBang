# encoding:utf-8
import re


inputfile = file('/home/zhipengwu/Innovation/BigBang/MachineLearningTutorial/resource/titanic/train.csv')

while True:
    line = inputfile.readline()
    splits = re.split(',', line)
    # 去掉分割后的字符串中的空格(或其他分隔符)
    print splits
    print ''
