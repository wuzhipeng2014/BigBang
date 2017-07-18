package com.qunar.hackathon2017;

import com.clearspring.analytics.util.Lists;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.commonutil.FileUtil;
import com.qunar.hackathon2017.bean.HotelComment;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhipengwu on 17-7-14.
 */
public class ExtractComment {

    public static Gson gson = new GsonBuilder().create();

    public static void main(String[] args) throws IOException {
        String inputFile = "/home/zhipengwu/data/机器学习/hackathon/基于用户评论的用户需求挖掘/hotel_comments.txt";
        String outputFile = "/home/zhipengwu/data/机器学习/hackathon/基于用户评论的用户需求挖掘/hotel_comments_contents.txt";

        FileWriter fw = new FileWriter(outputFile);
        try {
            Iterable<String> fileIterator = FileUtil.getFileIterator(inputFile);
            Iterator<String> iterator = fileIterator.iterator();

            List<HotelComment.Comment> commentList = Lists.newArrayList();

            Map<String, List<String>> hotelCommentsMap = Maps.newHashMap();

            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] split = line.split("\t");
                List list = gson.fromJson(split[3], commentList.getClass());
                String hotelSeq = split[2];
                if (Strings.isNullOrEmpty(hotelSeq)) {
                    continue;
                }
                ;
                for (Object item : list) {
                    String content = ((Map<String, String>) item).get("content");
                    String type = ((Map<String, String>) item).get("type");
                    if (type.equalsIgnoreCase("text") && !Strings.isNullOrEmpty(content) && content.length() > 2) {
                        if (hotelCommentsMap.containsKey(hotelSeq)) {
                            hotelCommentsMap.get(hotelSeq).add(content.replaceAll("\n", ""));
                        } else {
                            List<String> commentsList = Lists.newArrayList();
                            commentsList.add(content.replaceAll("\n", ""));
                            hotelCommentsMap.put(hotelSeq, commentsList);
                        }
                        // fw.append(content.replaceAll("\n",""));
                        // fw.append("\n");
                    }
                }
            }

            // 将酒店评论分别存储到文件中
            Set<String> hotelMapKeySets = hotelCommentsMap.keySet();
            int count=0;
            System.out.println(hotelMapKeySets.size());
            for (String key : hotelMapKeySets) {
                List<String> comments = hotelCommentsMap.get(key);
                String outputPath = String.format("/home/zhipengwu/data/机器学习/hackathon/基于用户评论的用户需求挖掘/hotelComments/%s",
                        key);
                if (comments.size()>200) {
                    FileWriter fileWriter = new FileWriter(outputPath);
                    for (String item : comments) {
                        fileWriter.append(item.toString());
                        fileWriter.append("\n");

                    }
                    fileWriter.close();
                    count++;
                }
            }
            System.out.println(count);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        List<String> lists = Lists.newArrayList();
        lists.add("hello");
        lists.add("world");
        System.out.println(lists.toString());
    }
}
