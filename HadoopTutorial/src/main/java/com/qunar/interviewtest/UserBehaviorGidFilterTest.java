package com.qunar.interviewtest;

/**
 * Created by zhipengwu on 17-2-20.
 */
public class UserBehaviorGidFilterTest {
    public static void main(String[] args) {
        String s="{\"simpleFlightPvLog\":{\"clickedFlightInfoList\":[],\"bookedFlightInfoList\":[],\"orderedFlightInfoList\":[],\"fromCity\":\"北京\",\"toCity\":\"海拉尔\",\"depDate\":\"20170314\",\"airlines\":[]},\"platform\":\"adr\",\"userCoordinate\":{\"x\":39.976727,\"y\":116.498318,\"z\":NaN},\"userCityName\":\"北京\",\"uid\":\"869465022565589\",\"vid\":\"60001155\",\"gid\":\"00032A37-6019-AEB6-EAB0-99395DB0FB64\",\"userName\":\"guyjply8196\",\"userId\":-1,\"phoneType\":\"NX531J\",\"pid\":\"10010\",\"cid\":\"C1033\",\"identifier\":\"00032A37-6019-AEB6-EAB0-99395DB0FB64\",\"actionTime\":\"20170218 00:37:59.000\"}\tcom.qunar.mobile.innovation.behavior.flight.FlightPvBehavior";
        String[] split = s.split("\\t");
        if (split.length==2){
            String className=split[1];
            try {
                Class<?> aClass = Class.forName(className);

                System.out.println();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println(split[1]);
    }
}
