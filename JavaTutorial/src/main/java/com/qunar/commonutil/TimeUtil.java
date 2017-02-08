package com.qunar.commonutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhipengwu on 16-8-29.
 */
public class TimeUtil {

  private static Logger logger= LoggerFactory.getLogger(TimeUtil.class);


    /**
     * 格式化日期格式,默认返回昨天的日期
     * @param inputTime yyyyMMdd类型或yyyy-MM-dd
     * @return yyyy-MM-dd类型日期格式
     */
    public static String formateTime(String inputTime){
        SimpleDateFormat df_in = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        SimpleDateFormat df_out = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String formatedTime="";
        if (null==inputTime||inputTime.isEmpty()){
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            return df_out.format(date);
        }
        if (inputTime.contains("-")){
            Date inputDate = null;
            try {
                inputDate = df_out.parse(inputTime);
            } catch (ParseException e) {
                logger.error("日期格式转换失败,{}",e);
            }
            formatedTime=df_out.format(inputDate);
        }else {

            try {
                Date inputDate = df_in.parse(inputTime);
                formatedTime = df_out.format(inputDate);
            } catch (Exception e) {
                logger.error("日期格式转换失败,{}",e);

            }
        }

        return formatedTime;
    }


    public static void main(String[] args) {
//        System.out.println(formateTime("2016081ff9"));
//        System.out.println(formateTime("dfhhfgtghhg"));
//        System.out.println(formateTime(null));
//        System.out.println(formateTime(""));

        String actionTime="2017-01-15 07:12:50:232";
        try {
            Date date = DateUtils.parseDate(actionTime,"yyyy-MM-dd HH:mm:ss:SSS");
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
