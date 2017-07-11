package com.qunar.StatisticTask.bean.cainixihuan;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-6-28.
 */
public class CainixihuanBean {
    public CParamsBean cParams;
    public CParamsBean cParam;
    public ClickItemBean clickItem;
    public String clickTime;
    public String clickType;
    public String detailLocation;
    public String input;
    public String requestId;

    public static CainixihuanBean getInstanceFromLog(String line){
        Gson gson=new GsonBuilder().create();
        if (!Strings.isNullOrEmpty(line)) {
            int index = line.indexOf("{");
            if (index>-1){
                String substring = line.substring(index);
                return gson.fromJson(substring,CainixihuanBean.class);
            }
        }
        return null;
    }


    public static void main(String[] args) {
        Gson gson=new GsonBuilder().create();
        String line="[2017-06-26 00:00:06]   {\"cParams\":{\"cid\":\"C2195\",\"gid\":\"424F3B71-A3DD-79AC-214E-85CB2C1A44C8\",\"lat\":\"\",\"lgt\":\"\",\"model\":\"LG-H818\",\"nt\":\"WIFI\",\"osVersion\":\"6.0\",\"pid\":\"10010\",\"uid\":\"359345061605297\",\"un\":\"gkeinho9736\",\"usid\":\"194504216\",\"vid\":\"60001169\"},\"clickItem\":{\"highLight\":0,\"originText\":\"7天酒店西安东大街张学良纪念馆东门店\",\"pattern\":\"hotel_name\",\"productID\":\"hotel_16_xian_8980\",\"score\":\"3.2574437576169997\",\"source\":\"recoByPv2Pv\",\"text\":\"7天酒店西安东大街张学良纪念馆东门店\",\"url\":\"://hotel/detail?fromForLog=95&isNotClosePre=1&needRoomVendor=0&needRecommendBar=1&sourceType=a_search_12&cityUrl=xian&fromDate=2017-06-28&toDate=2017-06-30&ids=xian_8980&currLatitude=&currLongitude=&\"},\"clickTime\":\"20170626000006028\",\"clickType\":\"_caihotel\",\"detailLocation\":0,\"input\":\"null\",\"requestId\":\"424F3B71-A3DD-79AC-214E-85CB2C1A44C8_1498406386635\"}";


        String lineIos="{\"cParam\":{\"uid\":\"DCA8547F-D68A-4020-A4A7-A133C078F46F\",\"usid\":\"244263580\",\"vid\":\"80011136\",\"cid\":\"C1001\",\"osVersion\":\"10.3.2\",\"lat\":\"30.666433\",\"gid\":\"31C0F087-70C8-8F1A-6FEA-A3EC6C78DA0E\",\"un\":\"uekrzex0171\",\"nt\":\"CTRadioAccessTechnologyLTE\",\"model\":\"iPhone9,2\",\"lgt\":\"104.051335\",\"pid\":\"10010\"},\"input\":\"null\",\"clickTime\":\"20170626000025327\",\"clickType\":\"_caihotel\",\"detailLocation\":0,\"requestId\":\"DCA8547F-D68A-4020-A4A7-A133C078F46F_1498406423077\",\"clickItem\":{\"source\":\"recoByOrder\",\"score\":3.908846616617,\"city\":\"成都\",\"indexStr\":\"龙堂庭院客栈成都宽窄巷子店\",\"highLight\":1,\"pattern\":\"hotel_name\",\"productID\":\"hotel_16_chengdu_1989\",\"text\":\"龙堂庭院客栈成都宽窄巷子店\",\"originText\":\"龙堂庭院客栈成都宽窄巷子店\",\"url\":\"://hotel/detail?fromForLog=95&isNotClosePre=1&needRoomVendor=0&sourceType=a_search_12&cityUrl=chengdu&fromDate=2017-06-26&toDate=2017-06-27&ids=chengdu_1989&currLatitude=30.666433&currLongitude=104.051335&\"}}";

        CainixihuanBean cainixihuanBean = CainixihuanBean.getInstanceFromLog(lineIos);
        CainixihuanBean cainixihuanBean2 = CainixihuanBean.getInstanceFromLog(line);


        System.out.println();

    }
}
