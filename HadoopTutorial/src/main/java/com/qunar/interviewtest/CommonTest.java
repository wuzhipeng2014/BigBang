package com.qunar.interviewtest;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.StatisticTask.bean.DiscoverBean;
import org.junit.Test;

/**
 * Created by zhipengwu on 17-4-25.
 */
public class CommonTest {

    public static Gson gson=new GsonBuilder().create();

    @Test
    public void test(){
        String line ="1493003012827*:hy:set*{\"data\":{\"bannershow\"\n" +
                ":\"免费试睡_170401\",\"id\":\"3277\",\"position\":\"1\",\"sid\":\"发现频道\",\"userfrom\":\"\"}}";

//        String line ="1493005043304*set*{\"data\"：{\"sid\"：\"发现频道\",\"userfrom\"：\"\",\"topic\"：\"destination_rank\",\"moduleshow\"：\"热门目的地\"}}";
//        action=line;
        String action=line.replaceAll("：", ":");

        if (!Strings.isNullOrEmpty(action) && action.contains("set")
                && action.contains("\"sid\"：\"发现频道\"")) {
            int index = action.indexOf("{");
            String subString = action.substring(index);
            String s = subString.replaceAll("：", ":");
//                    System.out.println(subString);
            DiscoverBean discoverBean = null;
            try {
                discoverBean = gson.fromJson(s, DiscoverBean.class);
            } catch (Exception e) {
                System.out.println(e);
            }

        }


//        if (!Strings.isNullOrEmpty(action)&&action.contains("set")&&action.contains("\"sid\":\"发现频道\"")){
//            int index = action.indexOf("{");
//            String substring = action.substring(index);
//            String s = StringEscapeUtils.unescapeJava(substring);
//
//            System.out.println(substring);
//
//            DiscoverBean discoverBean = gson.fromJson(s, DiscoverBean.class);
//            System.out.println();
//
//        }
    }
}
