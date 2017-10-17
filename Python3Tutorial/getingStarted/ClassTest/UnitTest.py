#encoding:utf-8

## 单元测试
import unittest
from MyUtils import formatedDate


# 编写一个单元测试类,从unittest.TestCase类继承
class TestMyUtil(unittest.TestCase):
    def test_formateDate(self):
        d={'first':1,'second':2}
        fdate= formatedDate()
        fdate2= formatedDate('2017-10-12')
        print(fdate)
        print(fdate2)
        ##单元测试中常用的断言
        ## 判断相等
        self.assertEqual(abs(-1),1)
        ## 判断是否抛出制定类型的Error
        with self.assertRaises(KeyError):
            value=d['empty']
        ## 判断是否抛出属性异常
        with self.assertRaises(AttributeError):
            d.empty



## 运行单元测试
if __name__=='__main__':
    unittest.main()

## 通过命令行参数运行单元测试

## python3 -m unittest UnitTest.py




