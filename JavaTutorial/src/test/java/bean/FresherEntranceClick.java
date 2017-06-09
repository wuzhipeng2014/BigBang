package bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-6-8.
 */
public class FresherEntranceClick {
    public String clickTime;
    public String clickItemName;
    public String module;
    public String clickPos;
    public String requestId;


    public static void main(String[] args) {
        Gson gson= new GsonBuilder().create();


        FresherEntranceClick fresherEntranceClick=new FresherEntranceClick();
        fresherEntranceClick.clickTime="20160613124629985";
        fresherEntranceClick.clickItemName="千元代金券";
        fresherEntranceClick.module="fresherEntrance";
        fresherEntranceClick.clickPos="1";
        fresherEntranceClick.requestId="requrestId";

        System.out.println(gson.toJson(fresherEntranceClick));
    }
}
