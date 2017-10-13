#encoding:utf-8

import logging
logging.basicConfig(level=logging.INFO)

##异常处理


def foo(s):
    n=int(s)
    print('n = %d' % n)
    logging.info('运行发生错误')
    print(10/n)





print(foo(0))


##使用pdb但不调试
## python3 -m pdb file.py

## pdb.set_trace() 相当于打断点