package bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhipengwu on 17-6-8.
 */
public class fresherEntranceShow {

    public String showTime;
    public String module;
    public String requestId;

    public static void main(String[] args) {
        fresherEntranceShow fresherEntranceShow=new fresherEntranceShow();
        fresherEntranceShow.showTime="20170613124629985";
        fresherEntranceShow.module="fresherEntrance";
        fresherEntranceShow.requestId="requestId";

        Gson gson=new GsonBuilder().create();
        System.out.println(
        gson.toJson(fresherEntranceShow));
    }

}
