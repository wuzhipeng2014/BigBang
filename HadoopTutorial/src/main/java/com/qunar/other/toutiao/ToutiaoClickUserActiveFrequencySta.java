package com.qunar.other.toutiao;

import com.google.common.collect.Maps;
import com.qunar.other.toutiao.bean.ClickUserStaInfo;
import com.qunar.other.toutiao.bean.ToutiaoUserInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.common.Strings;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Map;

import static com.qunar.util.GsonUtil.gson;

/**
 * Created by zhipengwu on 17-11-9. 头条活跃用户头条活跃次数统计
 */
public class ToutiaoClickUserActiveFrequencySta {
    public static void main(String[] args) {
        String inputFile = "/home/zhipengwu/secureCRT/toutiao_user_behavior_20171105";
        String outputFile=inputFile+"_userinfo_frequncy.txt";
        Map<String, ClickUserStaInfo> clickuserStaInfoMap = Maps.newHashMap();
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(inputFile));
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (Strings.isNullOrEmpty(line)) {
                    continue;
                }
                String time = line.substring(1, 20);
                String jsonContent = line.substring(line.indexOf("{"));
                if (line.contains("INFO userInfo")) {
                    ToutiaoUserInfo toutiaoUserInfo = gson.fromJson(jsonContent, ToutiaoUserInfo.class);
                    String md5KeyId = toutiaoUserInfo.md5KeyId;
                    if (!Strings.isNullOrEmpty(md5KeyId)) {
                        if (!clickuserStaInfoMap.containsKey(md5KeyId)) {
                            ClickUserStaInfo clickUserStaInfo = new ClickUserStaInfo();
                            clickUserStaInfo.userInfoTimeSet.add(time);
                            clickUserStaInfo.userInofNum = 1;
                            clickuserStaInfoMap.put(md5KeyId, clickUserStaInfo);

                        }else {
                            ClickUserStaInfo clickUserStaInfo = clickuserStaInfoMap.get(md5KeyId);
                            clickUserStaInfo.userInfoTimeSet.add(time);
                            clickUserStaInfo.userInofNum += 1;
                            clickuserStaInfoMap.put(md5KeyId,clickUserStaInfo);

                        }
                    }
                }
            }
            lineIterator.close();
            // 输出点击用户UserInfo日志出现次数

            FileWriter fileWriter=new FileWriter(outputFile);

            for (String k:clickuserStaInfoMap.keySet()){
                int userInofNum = clickuserStaInfoMap.get(k).userInofNum;
                String s = Arrays.toString(clickuserStaInfoMap.get(k).userInfoTimeSet.toArray());
                fileWriter.append(String.format("%s\t%s\t%s\n",k,userInofNum,s));
            }
            fileWriter.close();

        } catch (Exception e) {
        }
    }
}
