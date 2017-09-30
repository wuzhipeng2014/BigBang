package com.qunar.work.toutiao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.work.bean.ToutiaoClickLog;
import org.elasticsearch.common.Strings;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import static com.qunar.work.toutiao.LibsvmToCSV.isNum;

/**
 * Created by zhipengwu on 17-9-12.
 */
public class ToutiaoLogDesensitization {

    private static MessageDigest md5 = null;
   public static   Gson gson = new GsonBuilder().create();

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String baseDir="/home/zhipengwu/secureCRT/";
        String click_file_name = "toutiao_click_logdata_";
        String show_file_name = "toutiao_show_logdata_";
        String outputFile = "";

//        List<String> dateList = Arrays.asList("20170905", "20170906", "20170907", "20170908", "20170909", "20170910", "20170911");
        List<String> dateList = Arrays.asList("20170904");

        for (String date:dateList){
           String  click_inputFile=String.format("%s%s%s.gz",baseDir,click_file_name,date);
           String  click_outputFile=String.format("%s%s%s.result.txt",baseDir,click_file_name,date);
           String  show_inputFile=String.format("%s%s%s.gz",baseDir,show_file_name,date);
           String  show_outputFile=String.format("%s%s%s.result.txt",baseDir,show_file_name,date);
            desensitizeTouttiaoLog(click_inputFile,click_outputFile);
            desensitizeTouttiaoLog(show_inputFile,show_outputFile);

        }



//        String line = "[2017-09-07 06:37:04  INFO click:148] {\"channelId\":\"toutiao_pmp\",\"device_id\":\"861332035848675\",\"os\":\"adr\",\"md5_device_id\":\"77f3688d9fdc4a6ee6c4a858f1b9d037\",\"type\":\"click\",\"requestId\":\"20170906113557010008061034687E4E\",\"connection_type\":\"WIFI\",\"ip\":\"49.73.21.45\",\"productId\":\"7000001\",\"adType\":\"new\",\"department\":\"train\",\"price\":0.0,\"bannerType\":\"\",\"sequence\":\"\",\"adPos\":\"\"}";
//        String line2="[2017-09-07 00:13:33  INFO show:138] {\"channelId\":\"toutiao_rtb\",\"device_id\":\"861726033310176\",\"os\":\"adr\",\"md5_device_id\":\"cf843c7c5fa00e9c6025026538c9bf74\",\"type\":\"show\",\"requestId\":\"20170906141556172022000226857A6A\",\"connection_type\":\"WIFI\",\"ip\":\"183.130.126.6\",\"productId\":\"7000102\",\"adType\":\"new\",\"department\":\"hotel\",\"price\":560.0,\"bannerType\":\"\",\"sequence\":\"\",\"adPos\":\"\"}";
//
//        String clickLog = desensitizeToutiaoClickLog(line);
//
//
//        String showLog = desensitizeToutiaoClickLog(line2);




    }


    public static void desensitizeTouttiaoLog(String inputFile,String outputFile){
        try {
            InputStream in = new GZIPInputStream(new FileInputStream(inputFile));
            FileWriter fw=new FileWriter(outputFile);
            Scanner scanner=new Scanner(in);
            while (scanner.hasNextLine()){
                String line1 = scanner.nextLine();
                String s = desensitizeToutiaoClickLog(line1);
                if (!Strings.isNullOrEmpty(s)) {
                    fw.append(s + "\n");
                    fw.flush();
                }
            }
            in.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String desensitizeToutiaoClickLog(String line){
        String sepator = "] {";
        String replace = "";
        String result="";
        if (line.contains(sepator) && line.contains("\"adType\":\"new\"")) {
            int i = line.indexOf(sepator);
            if (i > 0) {
                String content = line.substring(i + 2);
                ToutiaoClickLog toutiaoClickLog = gson.fromJson(content, ToutiaoClickLog.class);
                toutiaoClickLog.channelId = getChannelId(toutiaoClickLog.channelId);
                toutiaoClickLog.department = getDepartment(toutiaoClickLog.department);
                toutiaoClickLog.md5_device_id = getMd5(toutiaoClickLog.md5_device_id);
                toutiaoClickLog.productId = getProductId(toutiaoClickLog.productId);
                toutiaoClickLog.price = getPrice(toutiaoClickLog.price);
                String s = gson.toJson(toutiaoClickLog);
                replace = s.replace("bannerType", "type");

            }
            String prefix = line.substring(0, i + 1);
            result = String.format("%s %s", prefix, replace);
        }

        return result;
    }




    /**
     * 用于获取一个String的md5值
     * 
     * @return
     */
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    public static String getChannelId(String channelid) {
        String result = "";
        if (!Strings.isNullOrEmpty(channelid)) {
            if (channelid.equalsIgnoreCase("toutiao_rtb")) {
                result = "channel001";
            } else if (channelid.equalsIgnoreCase("toutiao_pmp")) {
                result = "channel002";
            } else {
                result = "channel003";
            }
        }

        return result;
    }

    public static String getDepartment(String department) {
        String result = "";
        if (!Strings.isNullOrEmpty(department)) {
            if (department.equalsIgnoreCase("train")||department.equalsIgnoreCase("flight")) {
                result = "department001";
            } else if (department.equalsIgnoreCase("hotel")) {
                result = "department002";
            } else {
                result = "department003";
            }
        }
        return result;
    }

    public static String getProductId(String productId) {
        String result = "";
        if (!Strings.isNullOrEmpty(productId) && productId.length() > 3) {
            result = productId.substring(productId.length() - 3);
        } else {
            result = productId;
        }
        return result;
    }

    public static String getPrice(String price) {
        String result = "0";
        if (!Strings.isNullOrEmpty(price) && isNum(price)) {
            Double v = Double.valueOf(price) * 10;
            result = String.valueOf(v.intValue());
        }
        return result;
    }

}
