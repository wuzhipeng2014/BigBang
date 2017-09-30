package com.qunar.work.smallentrance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.work.bean.PromotionClickBean;

/**
 * Created by zhipengwu on 17-8-28.
 */
public class ClickLog {
    public String entranceName; //入口名字
    public String skipUrl; //跳转地址
    public String cardType; //卡片类型
    public String cardName; //卡片名称
    public PromotionClickBean.CommonParams commonParams;
    public SkipOuterParams skipOuterParams;


    public static void main(String[] args) {
        Gson gson=new GsonBuilder().create();
        ClickLog clickLog=new ClickLog();
        clickLog.entranceName="领券中心";
        clickLog.skipUrl="http://";
        clickLog.cardType="ticket";
        clickLog.commonParams=new PromotionClickBean.CommonParams();
        clickLog.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        clickLog.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        clickLog.commonParams.vid="60001166";
        clickLog.commonParams.pid="10000";
        SkipOuterParams skipOuterParams=new SkipOuterParams();
        skipOuterParams.cardType="traffic";
        skipOuterParams.in_track="***";
        clickLog.skipOuterParams=skipOuterParams;
        String s = gson.toJson(clickLog);
        System.out.println(s);

    }
}
