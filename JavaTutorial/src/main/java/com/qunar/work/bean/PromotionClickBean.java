package com.qunar.work.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-6-22.
 * 客户端首页出校活动日志格式
 */
public class PromotionClickBean {
    public String cat; //渠道号
    public String template; //模板名字
    public String activity; //活动名字
    public String module; //模块名字
    public String listName;
    public String listLocation;
    public String tab; //产品所属业务线名字
    public String clickType; //点击类型
    public String clickName; //点击名字
    public String detailLocation; //点击位置
    public CommonParams commonParams;

    public static class CommonParams{
       public   String gid;
        public String uid;
        public String vid;
        public String pid;
        public String userName;
    }


    public static void main(String[] args) {
        Gson gson=new GsonBuilder().create();
        PromotionClickBean promotionClickBean=new PromotionClickBean();
        promotionClickBean.commonParams=new CommonParams();
        promotionClickBean.cat="source1";
        promotionClickBean.template="template1";
        promotionClickBean.activity="楚乔出鞘,暑期大促";
        promotionClickBean.module="productList";
        promotionClickBean.listName="1元起秒";
        promotionClickBean.listLocation="0";
        promotionClickBean.tab="flight";
        promotionClickBean.clickType="product";
        promotionClickBean.clickName="青岛到上海";
        promotionClickBean.detailLocation="1";
        promotionClickBean.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        promotionClickBean.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        promotionClickBean.commonParams.vid="60001166";
        promotionClickBean.commonParams.pid="10000";

        System.out.println(gson.toJson(promotionClickBean));



    }

}
