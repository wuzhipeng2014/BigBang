package com.qunar;

import org.junit.Test;

/**
 * Created by zhipengwu on 17-2-8.
 */
public class test {
    public static void main(String[] args) {



        String s="hello world";
        boolean hello = s.contains("hello");

        String posFeature ="262e416ee12daff86974942e424dd89e\t0 53:1.0 88:1.0 145:1.0 153:1.0 217:1.0 223:1.0 227:1.0 254:1.0 637:1.0 642:1.0 644:1.0 670:1.0 703:1.0 712:1.0 821:1.0 837:1.0 918:1.0 940:1.0 1050:1.0 1057:1.0 1059:1.0 1069:1.0 1074:1.0 1083:1.0 1287:1.0 1296:1.0 1305:1.0 1313:1.0 1318:1.0 1327:1.0 1335:1.0 1343:1.0 1351:1.0 1360:1.0 1369:1.0 1377:1.0 1387:1.0 1399:1.0 1426:1.0 1428:1.0 1455:1.0 1467:1.0 1475:1.0 1498:1.0 1506:1.0 1511:1.0 1561:1.0 1615:1.0 3225:1.0 3251:1.0 3284:1.0 3293:1.0 3402:1.0 3418:1.0 3499:1.0 3521:1.0 4844:1.0 4871:1.0 4905:1.0 4940:1.0 5122:1.0 5353:1.0 5416:1.0 6179:1.0 6291:1.0 6858:1.0 7012:1.0 7782:1.0 7824:1.0 7828:0";
        if (posFeature.contains("\t0")){
            posFeature = posFeature.replace("\t0", "\t1");
        }

        System.out.printf(posFeature);

        int count=0;
        for (int i = 1; i < 9002; i++) {
            System.out.print(String.format("\"%s\",",i));
        }

//        Lists.newArrayList();

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
////        System.out.println(period.getMinutes());
//        DateTimeFormatter fmt= DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
//        DateTimeFormatter fmt1= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");
//
//        DateTime dateTime = fmt.parseDateTime("20170713080032000");
//        System.out.println( dateTime.toString(fmt1));
//        System.out.println(dateTime.getMillis());
//        System.out.println(DateTime.now().getMillis());

//        String s = dateTime1.toString(fmt);
//        System.out.println(s);





    }


    @Test
    public void swapVariable(){
        int a=10;
        int b=20;
        a=b+(b=a)*0;
        System.out.println(String.format("a=%s, b=%s",a,b));


    }
}
