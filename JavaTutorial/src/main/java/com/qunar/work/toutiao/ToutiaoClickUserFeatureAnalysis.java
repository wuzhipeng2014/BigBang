package com.qunar.work.toutiao;

import com.clearspring.analytics.util.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.work.bean.ToutiaoClickLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.common.Strings;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhipengwu on 17-11-6. 头条有点击用户特征分析
 */
public class ToutiaoClickUserFeatureAnalysis {
    public static class ClickInfo{
        public List<String> clickTime;
        public int clickNum;
    }

    public static Gson gson = new GsonBuilder().create();
   public static DateTimeFormatter fmt= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");



    public static void main(String[] args) {
        String inputFile = "/home/zhipengwu/secureCRT/click.2017-11-05.log";
        parseClickLogFeature(inputFile);
    }

    public static void parseClickLogFeature(String fileName) {

        Map<String,ClickInfo> clickInfoList= Maps.newHashMap();


        String outputFile = fileName + ".out";
        String outputFile2 = fileName + ".sta";
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(fileName));
            FileWriter fw = new FileWriter(outputFile);
            FileWriter fw2 = new FileWriter(outputFile2);
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                String time = line.substring(1, 20);
                line = line.substring(line.indexOf("{"));
                ToutiaoClickLog toutiaoClickLog = gson.fromJson(line, ToutiaoClickLog.class);
                if (toutiaoClickLog != null && toutiaoClickLog.adType != null && toutiaoClickLog.adType.equals("new")) {
                    String format = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\n", time,toutiaoClickLog.md5_device_id,
                            toutiaoClickLog.department, toutiaoClickLog.productId, toutiaoClickLog.sequence,
                            toutiaoClickLog.adPos, toutiaoClickLog.price);
                    if (!Strings.isNullOrEmpty(format)) {
                        fw.append(format);
                    }

                    if (clickInfoList.containsKey(toutiaoClickLog.md5_device_id)){
                        clickInfoList.get(toutiaoClickLog.md5_device_id).clickNum++;
                        clickInfoList.get(toutiaoClickLog.md5_device_id).clickTime.add(time);

                    }else {
                        ClickInfo clickInfo=new ClickInfo();
                        clickInfo.clickTime= Lists.newArrayList();
                        clickInfo.clickTime.add(time);
                        clickInfo.clickNum=1;
                        clickInfoList.put(toutiaoClickLog.md5_device_id,clickInfo);
                    }
                }



            }

            for (String keyid : clickInfoList.keySet()){
                ClickInfo clickInfo = clickInfoList.get(keyid);

                long l=-1;
                if (clickInfo.clickNum>1){
                    Date dateTime1 = fmt.parseDateTime(clickInfo.clickTime.get(0)).toDate();
                    Date dateTime2 = fmt.parseDateTime(clickInfo.clickTime.get(1)).toDate();

                    l =Math.abs(dateTime1.getTime() - dateTime2.getTime());

                }
                String format = String.format("%s\t%s\t%s\t%s\n", keyid,clickInfo.clickNum, Arrays.toString(clickInfo.clickTime.toArray()),l);
                if (!Strings.isNullOrEmpty(format)) {
                    fw2.append(format);
                }
            }

            fw.close();
            fw2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
