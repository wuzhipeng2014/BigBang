package com.qunar.work;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;
import com.qunar.commonutil.FileUtil;

/**
 * Created by zhipengwu on 17-1-23.
 */
public class ods_client_behavior_hour {

    public static void main(String[] args) {
        String line = "2017-01-19      04:13:12        2017-01-19      04:13:12.713    2017-01-19      04:13:06.585    2017-01-19      04:13:05.872    1484770386585*set*ZHOU_BIAN_YOU_mht6j_START####{\"tab\"：\"全部\",\"productType\"：\"dujia\",\"productTag\"：\"景加酒\",\"itemName\"：\"北京九华山庄贵宾楼大酒店-贵宾楼标准间-浪漫私汤双人套票+双人军都滑雪票\",\"clickTime\"：\"20170119041306585\",\"selectCity\"：\"北京\",\"poiCity\"：\"\",\"requestId\"：\"16E54376-DEB1-14AD-8C66-EAD29F90C50F_1484770268110\",\"clickPos\"：\"6\",\"clickType\"：\"middlePageEntrance\"}####ZHOU_BIAN_YOU_mht6j_END     C1001   FD44596B-B6A7-4F88-9371-4E3B6F84EDE3    80011129        10010           FD44596B-B6A7-4F88-9371-4E3B6F84EDE3    9.3.2   iPhone7,2       111.22.119.79   utzwsba3590";

        String baseDir = "/home/zhipengwu/secureCRT/";
//        String filename = "ZHOU_BIAN_YOU_mht6j_START_ios_20170119";
        String filename = "HOMEPAGE_MODULE_SHOW_bn39sa_START_ios_20170121";
//        String filename = "RECOMMEND_CLICK_YmVmY_START_ios_20170119";
        String inputFilename = baseDir + filename + ".gz";
        String outputFilename = baseDir + "ods_client_behavior_hour/"+filename + ".txt";
        Scanner gzFileScaner = null;
        try {
            gzFileScaner = FileUtil.getGzFileScaner(inputFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = Lists.newArrayList();
        int countGid = 0;
        int countUid = 0;
        int countCuid = 0;
        int countExpLine=0;
        while (gzFileScaner.hasNextLine()) {
            String s = gzFileScaner.nextLine();
            String[] split = s.split("\t");
            if (split.length > 14) {
                String cuid = split[14];
                String uid = split[10];
                String gid = split[13];

                list.add(cuid);
                if (list.size() > 1024) {
                    FileUtil.writeFile(list, outputFilename);
                    list.clear();
                }
                if (gid.length() < 1) {
                    countGid++;
                }
//                if (cuid.length() < 1 || cuid.equals("0000000000000000000000000000000000000000")
//                        || cuid.equals("00000000-0000-0000-0000-000000000000")) {
//                    countCuid++;
//                }
//                if (uid.length() < 1 || uid.equals("0000000000000000000000000000000000000000")
//                        || uid.equals("00000000-0000-0000-0000-000000000000")) {
//                    countUid++;
//                }
            }else{
                countExpLine++;
            }

        }
        FileUtil.writeFile(list,outputFilename);
        list.clear();

        System.out.println("######countExpLine="+countExpLine);
        System.out.println("####countgid:"+countGid);


    }
}
