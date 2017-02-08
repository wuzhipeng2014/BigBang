package com.qunar.commonutil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Created by zhipengwu on 16-8-26.
 */
public class RegExpUtil {

    public static final int ETL_CLIENT_LENGTH=22;



    //根据pattern匹配指定的字符串resource,并返回匹配列表
    public static List<String> MatchReg(Pattern pattern, String resource){
        Matcher matcher = pattern.matcher(resource);
        List<String> stringList= Lists.newArrayList();
        while (matcher.find()){
            String group = matcher.group();
//            System.out.println(group);
            stringList.add(group);
        }
        return stringList;
    }

    public static String getPrefix(String targetRegExp){
        Preconditions.checkNotNull(targetRegExp);
        String prefix="";
        int openBraceIndex = targetRegExp.indexOf("{");
        if (openBraceIndex>0) {
            prefix = targetRegExp.substring(0, openBraceIndex - 1);
        }
        return prefix;
    }

    public static String getSuffix(String targetRegExp){
        Preconditions.checkNotNull(targetRegExp);
        String suffix="";
        int openBraceIndex = targetRegExp.indexOf("}");
        if (openBraceIndex>0) {
            suffix = targetRegExp.substring(openBraceIndex + 1);
        }
        return suffix;
    }

    /**
     * 组装指定格式的前缀
     * @param prefix
     * @param gid
     * @param vid
     * @return
     */
    public static String packagePrefix(String prefix, String gid, String vid){
        String result= String.format("%s{\"gid\":\"%s\",\"vid\":\"%s\",", prefix, gid, vid);
        return result;
    }

    public static String packagePrefix(String prefix, String[] split){
        String result="";
        if (split.length>20){
            int len=split.length;
            String dt=split[1];
            String cid=split[4];
            String uid=split[5];
            String cuid=split[6];
            String vid = split[7];
            String pid=split[8];
            String gid = split[9];
            String param = split[16];
            String username=split[20];
            result= String.format("%s{\"gid\":\"%s\",\"vid\":\"%s\",\"cid\":\"%s\",\"uid\":\"%s\",\"cuid\":\"%s\",\"pid\":\"%s\",\"username\":\"%s\",\"dt\":\"%s\",", prefix, gid, vid,cid,uid,cuid,pid,username,dt);
        }
        return result;
    }
}
