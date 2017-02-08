package com.qunar.AbnormalPOI.bean;


import com.google.common.base.Preconditions;

/**
 * Created by zhipengwu on 16-10-31.
 */
public class MyTime {
    public MyTime(){}


    int day;
    int hour;
    int minute;
    int second;
    int millisecond;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
    }

    //time格式： “20161028 17:04:17.193”
    public void setMyTime(String time){
        Preconditions.checkNotNull(time);
        String actionTime = time;
         millisecond=Integer.valueOf(actionTime.substring(18));
         second=Integer.valueOf (actionTime.substring(15,17));
         minute=Integer.valueOf( actionTime.substring(12,14));
         hour=Integer.valueOf(actionTime.substring(9,11));
         day=Integer.valueOf(actionTime.substring(6,8));
    }

    public boolean isSameTime( MyTime time2){
        MyTime time1=this;

        //判断时间是否相等
        int minute1 = time1.getMinute();
        int second1 = time1.getSecond();
        int minute2 = time2.getMinute();
        int second2 = time2.getSecond();
        int secondVariation=Math.abs(minute2*60+second2-minute1*60+second1);
        if (time1.getDay()==time2.getDay()&&time1.getHour()==time2.getHour()){
            if (Math.abs(secondVariation)<10){
                return true;
            }
        }
        return  false;
    }


}
