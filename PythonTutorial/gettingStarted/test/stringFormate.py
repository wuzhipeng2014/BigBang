#encoding:utf-8


file = open('/home/zhipengwu/secureCRT/format_top_1000_feature.txt')
lines = file.readlines()
target_cat_feats=[]
for line in lines:
    target_cat_feats.append(line)
print(target_cat_feats)



NR_THREAD = 1
train_file_name='toutiao_hotel_tr.csv'
test_file_name='toutiao_hotel_te.csv'

t='test111'
train_file_head=train_file_name.split(".")[0]
test_file_head=test_file_name.split(".")[0]

print '1-----------'
cmd = 'converters/parallelizer-a.py -s {nr_thread} converters/pre-a.py toutiao_hotel_tr.csv toutiao_hotel_tr.gbdt.dense toutiao_hotel_tr.gbdt.sparse'.format(nr_thread=NR_THREAD,test=t)

print cmd

cmd = 'converters/parallelizer-a.py -s {nr_thread} converters/pre-a.py {tr_file_name} {tr_file_head}.gbdt.dense {tr_file_head}.gbdt.sparse'.format(nr_thread=NR_THREAD,tr_file_name=train_file_name,tr_file_head=train_file_head)

print  cmd


print '2-----------'

cmd = 'converters/parallelizer-a.py -s {nr_thread} converters/pre-a.py toutiao_hotel_tr.csv toutiao_hotel_tr.gbdt.dense toutiao_hotel_tr.gbdt.sparse'.format(nr_thread=NR_THREAD)

print cmd

cmd = 'converters/parallelizer-a.py -s {nr_thread} converters/pre-a.py {tr_file_name} {tr_file_head}.gbdt.dense {tr_file_head}.gbdt.sparse'.format(nr_thread=NR_THREAD,tr_file_name=train_file_name,tr_file_head=train_file_head)

print cmd

print '3-----------'

cmd = './gbdt -t 30 -s {nr_thread} toutiao_hotel_te.gbdt.dense toutiao_hotel_te.gbdt.sparse toutiao_hotel_tr.gbdt.dense toutiao_hotel_tr.gbdt.sparse toutiao_hotel_te.gbdt.out toutiao_hotel_tr.gbdt.out'.format(nr_thread=NR_THREAD)

print cmd

cmd = './gbdt -t 30 -s {nr_thread} toutiao_hotel_te.gbdt.dense toutiao_hotel_te.gbdt.sparse toutiao_hotel_tr.gbdt.dense toutiao_hotel_tr.gbdt.sparse toutiao_hotel_te.gbdt.out toutiao_hotel_tr.gbdt.out'.format(nr_thread=NR_THREAD)


print cmd