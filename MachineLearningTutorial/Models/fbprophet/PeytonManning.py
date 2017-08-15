# encoding:utf-8
import pandas as pd
import numpy as np
from fbprophet import Prophet


# Python
df = pd.read_csv('../../resource/fbprophet/example_wp_peyton_manning.csv')
df['y'] = np.log(df['y'])
print df.head()



# Python
print '------------训练模型-----------'
m = Prophet()
m.fit(df);


# Python
print '------------构造预测数据------------'
future = m.make_future_dataframe(periods=365)
print future.tail()

# Python
print '------------开始预测数据------------'
forecast = m.predict(future)
print forecast[['ds', 'yhat', 'yhat_lower', 'yhat_upper']].tail()


# Python
m.plot(forecast)



print '预测完成'


# Python
m.plot_components(forecast);




