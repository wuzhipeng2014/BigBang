package com.qunar.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

/**
 * Created by zhipengwu on 17-11-8.
 */
public class GsonUtil {
    public static Gson gson= new GsonBuilder().create();
    public static Logger logger= LoggerFactory.getLogger(GsonUtil.class);
    public static DateTimeFormatter toutiaoDateFormatter = DateTimeFormat.forPattern("yyyyMMddHH");
    public static DecimalFormat df = new DecimalFormat("######0.00");
}
