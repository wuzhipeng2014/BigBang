package com.qunar;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by zhipengwu on 17-2-8.
 */
public class test {
    public static void main(String[] args) {
        Lists.newArrayList();

//        String time="1499904016786";
////        DateTime dateTime = TimeUtil.getDateTime(time);
//        DateTime dateTime1=new DateTime(Long.valueOf(time));
//        DateTime now = DateTime.now();
//        System.out.println(now.getMillis());
//        long l = now.getMillis() - Long.valueOf(time);
//
//        System.out.println("时间差:"+l);
//
//        Period period = new Period(dateTime1, now, PeriodType.millis());
//        System.out.println("输出间隔秒");
//        period.toStandardSeconds();
//
//
//        System.out.println(period.getHours());
//        System.out.println(period.getMinutes());
//        System.out.println(period.getSeconds());
//        System.out.println(period.getMillis());
//
//        System.out.println(period.getMinutes());
        DateTimeFormatter fmt= DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
        DateTimeFormatter fmt1= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");

        DateTime dateTime = fmt.parseDateTime("20170713080032000");
        System.out.println( dateTime.toString(fmt1));
        System.out.println(dateTime.getMillis());
        System.out.println(DateTime.now().getMillis());

//        String s = dateTime1.toString(fmt);
//        System.out.println(s);





    }
}
