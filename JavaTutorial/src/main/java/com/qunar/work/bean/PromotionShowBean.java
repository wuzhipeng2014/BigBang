package com.qunar.work.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-6-22.
 */
public class PromotionShowBean {
    public String cat; //渠道号
    public String template;
    public String activity;
    public PromotionClickBean.CommonParams commonParams;


    public static void main(String[] args) {
        Gson gson=new GsonBuilder().create();
        PromotionShowBean promotionClickBean=new PromotionShowBean();
        promotionClickBean.commonParams=new PromotionClickBean.CommonParams();

        promotionClickBean.cat="source1";
        promotionClickBean.template="template1";
        promotionClickBean.activity="楚乔出鞘,暑期大促";
        promotionClickBean.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        promotionClickBean.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        promotionClickBean.commonParams.vid="60001166";
        promotionClickBean.commonParams.pid="10000";

        System.out.println(gson.toJson(promotionClickBean));
    }
}
