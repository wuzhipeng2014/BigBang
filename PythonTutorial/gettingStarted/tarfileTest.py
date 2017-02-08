# Filename: tarfileTest.py
import tarfile
import time
import os

starttime = time.time()
tar = tarfile.open('/home/zhipengwu/data/backup.tar', 'w:gz')
for root, dir, files in os.walk('/home/zhipengwu/data/testoutput/'):
    for file in files:
        fullpath = os.path.join(root, file)
        tar.add(fullpath, arcname=file)
tar.close()
print time.time() - starttime
