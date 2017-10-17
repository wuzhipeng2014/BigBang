#encoding:utf-8

import subprocess



def printNum(n):
    i=0
    while i<n:
        print(i)
        i+=1


# subprocess.call(['printNum','10'])

print('$ nslookup www.python.org')

## 运行外部命令
r=subprocess.call(['nslookup','www.tmall.com'])
print('exit code:',r)


## 子进程输入参数

print('$ nslookup ')


p=subprocess.Popen(['nslookup'],stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE)

output,err=p.communicate(b'set q=mx\npython.org\nexit\n')
print(output.decode('utf-8'))
print('Exit code ',p.returncode)


