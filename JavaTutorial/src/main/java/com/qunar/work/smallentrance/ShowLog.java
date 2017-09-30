package com.qunar.work.smallentrance;

import com.clearspring.analytics.util.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.work.bean.PromotionClickBean;

import java.util.List;

/**
 * Created by zhipengwu on 17-8-28.
 */
public class ShowLog {
    public String pageName; //入口名字
    public String poiCity; //当前城市
    public List<CardBean> cards;
    public PromotionClickBean.CommonParams commonParams;

    public static void main(String[] args) {
        Gson gson=new GsonBuilder().create();

        ShowLog showLog=new ShowLog();
        showLog.pageName="更多服务";
        showLog.poiCity="北京";
        showLog.cards= Lists.newArrayList();
        CardBean cardBean=new CardBean();
        cardBean.cardType="度假";
        SkipOuterParams skipOuterParams=new SkipOuterParams();
        skipOuterParams.cardType="ticket";
        skipOuterParams.in_track="***";
        cardBean.entrances=Lists.newArrayList();
        EntranceBean entranceBean1=new EntranceBean();
        entranceBean1.entranceName="领券中心";
        entranceBean1.skipUrl="http://";
        entranceBean1.businessType="ticket";
        entranceBean1.skipOuterParams=skipOuterParams;
        cardBean.entrances.add(entranceBean1);
        EntranceBean entranceBean2=new EntranceBean();
        entranceBean2.entranceName="亲子游";
        entranceBean2.skipUrl="http://";
        entranceBean2.skipOuterParams=skipOuterParams;
        entranceBean2.businessType="ticket";
        cardBean.entrances.add(entranceBean2);



        CardBean cardBean2=new CardBean();
        cardBean2.cardType="度假";
        SkipOuterParams skipOuterParams2=new SkipOuterParams();
        skipOuterParams2.cardType="vacation";
        skipOuterParams2.entrance="***";
        skipOuterParams2.it="***";
        skipOuterParams2.et="***";
        cardBean2.entrances=Lists.newArrayList();
        EntranceBean entranceBean3=new EntranceBean();
        entranceBean3.entranceName="踏青赏花";
        entranceBean3.skipUrl="http://";
        entranceBean3.skipOuterParams=skipOuterParams2;
        entranceBean3.businessType="vacation";
        cardBean2.entrances.add(entranceBean2);


        showLog.commonParams=new PromotionClickBean.CommonParams();
        showLog.commonParams.gid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        showLog.commonParams.uid="0B4B9810-BEB9-57D0-C338-1EB5D89E8621";
        showLog.commonParams.vid="60001166";
        showLog.commonParams.pid="10000";
        showLog.cards.add(cardBean);

        showLog.cards.add(cardBean2);

        System.out.println(gson.toJson(showLog));


    }
}
