package com.qunar.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.mobile.innovation.behavior.train.TrainPvBehavior;

/**
 * Created by zhipengwu on 16-12-27.
 */
public class test {
    public static void main(String[] args) {

        Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss.SSS").serializeSpecialFloatingPointValues().create();
        String line ="{\"simpleTrainPvLog\":{\"logTime\":\"20161224 14:10:09.272\",\"fromCity\":\"广州\",\"toCity\":\"长沙\",\"depDate\":\"20170116\",\"pvedList\":[],\"url\":\"StationQueryService.getS2sData\"},\"platform\":\"adr\",\"userCoordinate\":{\"x\":23.005751,\"y\":113.706746,\"z\":NaN},\"userCityName\":\"东莞\",\"uid\":\"867676021345797\",\"vid\":\"60001153\",\"gid\":\"001352E6-073F-328E-D10D-F0963318A441\",\"userName\":\"307953011\",\"userId\":5894534,\"phoneType\":\"H60-L01\",\"pid\":\"10010\",\"cid\":\"C1012\",\"identifier\":\"001352E6-073F-328E-D10D-F0963318A441\",\"actionTime\":\"20161224 14:10:09.272\"}    com.qunar.mobile.innovation.behavior.train.TrainPvBehavior";
       String trainOrderPrefix="com.qunar.mobile.innovation.behavior.train.TrainPvBehavior";

        String s = line.replaceAll(trainOrderPrefix, "").trim();

        TrainPvBehavior trainPvBehavior = gson.fromJson(s, TrainPvBehavior.class);
        System.out.println(trainPvBehavior.identifier);

    }
}
