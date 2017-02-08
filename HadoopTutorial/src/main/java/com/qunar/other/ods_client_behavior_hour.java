package com.qunar.other;

/**
 * Created by zhipengwu on 17-1-23.
 */
public class ods_client_behavior_hour {


    public static void main(String[] args) {
        String line="2017-01-19      04:13:12        2017-01-19      04:13:12.713    2017-01-19      04:13:06.585    2017-01-19      04:13:05.872    1484770386585*set*ZHOU_BIAN_YOU_mht6j_START####{\"tab\"：\"全部\",\"productType\"：\"dujia\",\"productTag\"：\"景加酒\",\"itemName\"：\"北京九华山庄贵宾楼大酒店-贵宾楼标准间-浪漫私汤双人套票+双人军都滑雪票\",\"clickTime\"：\"20170119041306585\",\"selectCity\"：\"北京\",\"poiCity\"：\"\",\"requestId\"：\"16E54376-DEB1-14AD-8C66-EAD29F90C50F_1484770268110\",\"clickPos\"：\"6\",\"clickType\"：\"middlePageEntrance\"}####ZHOU_BIAN_YOU_mht6j_END     C1001   FD44596B-B6A7-4F88-9371-4E3B6F84EDE3    80011129        10010           FD44596B-B6A7-4F88-9371-4E3B6F84EDE3    9.3.2   iPhone7,2       111.22.119.79   utzwsba3590";

        String[] split = line.split("\t");


        System.out.println(split.length);


    }
}
