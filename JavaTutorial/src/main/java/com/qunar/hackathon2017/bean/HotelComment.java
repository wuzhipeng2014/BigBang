package com.qunar.hackathon2017.bean;

import com.clearspring.analytics.util.Lists;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by zhipengwu on 17-7-14.
 */
public class HotelComment {

    public static Gson gson=new GsonBuilder().create();

    public String id;
    public String hotelSeq;
    public String hotelName;
    public List<Comment> commentList;


    public HotelComment(String line){
        if (!Strings.isNullOrEmpty(line)){
            String[] split = line.split("\t");
            if (split.length==4){
                id=split[0];
                hotelSeq=split[1];
                hotelName=split[2];
                commentList= Lists.newArrayList();
                List<Comment> list = gson.fromJson(split[3], commentList.getClass());
            }
        }
    }

    public static class Comment {
        public String content;
        public String type;
    }
}
