package com.qunar.other.toutiao;

import com.google.common.collect.Maps;
import com.qunar.other.toutiao.bean.ToutiaoClickLog;
import com.qunar.other.toutiao.bean.ToutiaoShowLog;
import com.qunar.other.toutiao.bean.ToutiaoUserInfo;
import com.qunar.util.GsonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.common.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static com.qunar.util.GsonUtil.df;
import static com.qunar.util.GsonUtil.gson;
import static com.qunar.util.GsonUtil.logger;

/**
 * Created by zhipengwu on 17-11-8. 头条广告有点击用户特征分析
 */
public class ToutiaoClickUserBehaviorAna {
    public static Map<String, String> PHONE_MODEL_PRICE_MAP = Maps.newHashMap();
    static {
        loadPhonePrice();
    }

    public static Map<String, Integer> MISSING_MODEL_MAP = Maps.newTreeMap();

    public static void main(String[] args) {
        String inputFile = "/home/zhipengwu/secureCRT/toutiao_user_behavior_20171101";
        String clickOutputFile = inputFile + "_click.txt";
        String showOutputFile = inputFile + "_show.txt";
        Map<String, ToutiaoUserInfo> userInfoMap = Maps.newHashMap();
        try {
            FileWriter fw_click = new FileWriter(clickOutputFile);
            FileWriter fw_show = new FileWriter(showOutputFile);
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
                        if (!userInfoMap.containsKey(md5KeyId)) {
                            userInfoMap.put(md5KeyId, toutiaoUserInfo);
                        }
                    }
                }
            }
            lineIterator.close();
            lineIterator = FileUtils.lineIterator(new File(inputFile));
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (Strings.isNullOrEmpty(line)) {
                    continue;
                }
                String time = line.substring(1, 20);
                String jsonContent = line.substring(line.indexOf("{"));
                if (line.contains("INFO click")) {
                    ToutiaoClickLog toutiaoClickLog = gson.fromJson(jsonContent, ToutiaoClickLog.class);
                    ToutiaoUserInfo toutiaoUserInfo = userInfoMap.get(toutiaoClickLog.md5_device_id);
                    if (toutiaoClickLog == null) {
                        continue;
                    }
                    if (toutiaoUserInfo != null) {
                        toutiaoClickLog.yob = toutiaoUserInfo.yob;
                        toutiaoClickLog.gender = toutiaoUserInfo.gender;
                        toutiaoClickLog.nowCity = toutiaoUserInfo.nowCity;
                        toutiaoClickLog.os = toutiaoUserInfo.os;
                        toutiaoClickLog.osVersion = toutiaoUserInfo.osVersion;
                        toutiaoClickLog.model = toutiaoUserInfo.model;
                        try {
                            toutiaoClickLog.phoneprice = PHONE_MODEL_PRICE_MAP.get(toutiaoUserInfo.model);
                            toutiaoClickLog.phoneLevel = df
                                    .format(Math.ceil(Double.valueOf(toutiaoClickLog.phoneprice) / 500));
                        } catch (Exception e) {
                            logger.error("获取手机价格失败" + toutiaoUserInfo.model, e);
                        }
                    }
                    fw_click.append("click:" + gson.toJson(toutiaoClickLog) + "\n");
                } else if (line.contains("INFO show")) {
                    ToutiaoShowLog toutiaoShowLog = gson.fromJson(jsonContent, ToutiaoShowLog.class);
                    ToutiaoUserInfo toutiaoUserInfo = userInfoMap.get(toutiaoShowLog.md5_device_id);
                    if (toutiaoShowLog == null) {
                        continue;
                    }
                    if (toutiaoUserInfo != null) {
                        toutiaoShowLog.yob = toutiaoUserInfo.yob;
                        toutiaoShowLog.gender = toutiaoUserInfo.gender;
                        toutiaoShowLog.nowCity = toutiaoUserInfo.nowCity;
                        toutiaoShowLog.os = toutiaoUserInfo.os;
                        toutiaoShowLog.osVersion = toutiaoUserInfo.osVersion;
                        toutiaoShowLog.model = toutiaoUserInfo.model;
                        try {

                            toutiaoShowLog.phoneprice = PHONE_MODEL_PRICE_MAP.get(toutiaoUserInfo.model);
                            toutiaoShowLog.phoneLevel = df
                                    .format(Math.ceil(Double.valueOf(toutiaoShowLog.phoneprice) / 500));
                        } catch (Exception e) {
                            String[] split = toutiaoUserInfo.model.split(" |-|,");
                            if (MISSING_MODEL_MAP.containsKey(split[0])) {
                                Integer integer = MISSING_MODEL_MAP.get(split[0]);
                                MISSING_MODEL_MAP.put(split[0], integer + 1);
                            } else {
                                MISSING_MODEL_MAP.put(split[0], 1);
                            }
                            logger.error("获取手机价格失败 " + toutiaoUserInfo.model, e);
                        }
                    }
                    fw_show.append("show:" + gson.toJson(toutiaoShowLog) + "\n");
                }
            }

            System.out.println("缺失手机价格个数: " + MISSING_MODEL_MAP.size());
            for (String k : MISSING_MODEL_MAP.keySet()) {
                System.out.println(k + ":" + MISSING_MODEL_MAP.get(k));
            }
            fw_click.close();
            fw_show.close();
            lineIterator.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void loadPhonePrice() {
        String line = "";
        try {

            InputStream resourceAsStream = ToutiaoClickUserBehaviorAna.class.getClassLoader()
                    .getResourceAsStream("phone_price.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split != null && split.length == 3) {
                    PHONE_MODEL_PRICE_MAP.put(split[1].trim().toLowerCase(), split[2]);
                }
            }

        } catch (IOException e) {
            GsonUtil.logger.error("初始化PHONE_MODEL_PRICE_MAP失败:", e);
        }
    }
}
