#!/bin/sh

if [ -n "$1" ]
then
        yyyymmdd=`date +'%Y%m%d' -d $1`
        dd=`date +'%d' -d $1`
else
        yyyymmdd=`date +"%Y%m%d" -d "-1 days"`
        dd=`date +"%d" -d "-1 days"`
fi


 sudo -uwirelessdev hadoop jar /home/q/zhipeng.wu/share/HadoopTutorial-jar-with-dependencies.jar com.qunar.other.toutiao.GenerateToutiaoUserBehaviorContext  marmot_running/mr-in/toutiao_userInfo_address/all/201711/$dd/*/*,log/ads/click/$yyyymmdd,log/ads/show/$yyyymmdd zhipeng.wu/testdata/toutiao_user_behavior_no_click/$yyyymmdd 64


sudo -uwirelessdev hadoop fs -getmerge zhipeng.wu/testdata/toutiao_user_behavior/$yyyymmdd/*.gz /home/q/zhipeng.wu/toutiao_user_behavior_no_click_$yyyymmdd.gz

sz /home/q/zhipeng.wu/toutiao_user_behavior_no_click_$yyyymmdd.gz



 sudo -uwirelessdev hadoop jar /home/q/zhipeng.wu/share/HadoopTutorial-jar-with-dependencies.jar com.qunar.other.toutiao.GenerateToutiaoUserBehaviorContext  marmot_running/mr-in/toutiao_userInfo_address/all/201711/*/*/*,log/ads/click/20171105,log/ads/show/20171105,log/ads/show/20171104,log/ads/show/20171103,log/ads/show/20171102,log/ads/show/20171101 zhipeng.wu/testdata/toutiao_user_behavior/20171105_2 64





