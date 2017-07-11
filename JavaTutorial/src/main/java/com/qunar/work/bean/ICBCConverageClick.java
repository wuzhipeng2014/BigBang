package com.qunar.work.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-7-11.
 */
public class ICBCConverageClick {
    public String cat;
    public String moudle;
    public int pos;
    public String city;
    public String clickType;
    public String clickName;
    public PromotionClickBean.CommonParams commonParams;


    public static void main(String[] args) {
        ICBCConverageClick icbcConverageClick=new ICBCConverageClick();
        icbcConverageClick.commonParams=new PromotionClickBean.CommonParams();
        icbcConverageClick.cat="cat1";
        icbcConverageClick.moudle="推荐单品";
        icbcConverageClick.pos=1;
        icbcConverageClick.clickType="product";
        icbcConverageClick.clickName="北京九华山16区1晚";
        icbcConverageClick.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        icbcConverageClick.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        icbcConverageClick.commonParams.vid="60001166";
        icbcConverageClick.commonParams.pid="10000";
        icbcConverageClick.city="北京";


        Gson gson=new GsonBuilder().create();
        System.out.println( gson.toJson(icbcConverageClick));
    }


}
