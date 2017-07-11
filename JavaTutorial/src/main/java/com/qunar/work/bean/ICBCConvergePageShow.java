package com.qunar.work.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-7-11.
 */
public class ICBCConvergePageShow {
        public String cat;
        public String page;
        public String requestId;
    public String city;

    public PromotionClickBean.CommonParams commonParams;

    public static void main(String[] args) {
        ICBCConvergePageShow icbcConvergePageShow=new ICBCConvergePageShow();
        icbcConvergePageShow.commonParams=new PromotionClickBean.CommonParams();
        icbcConvergePageShow.cat="cat1";
        icbcConvergePageShow.page="机酒旅行";
        icbcConvergePageShow.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        icbcConvergePageShow.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        icbcConvergePageShow.commonParams.vid="60001166";
        icbcConvergePageShow.commonParams.pid="10000";
        icbcConvergePageShow.city="北京";

        Gson gson=new GsonBuilder().create();
        System.out.println( gson.toJson(icbcConvergePageShow));
    }
}
