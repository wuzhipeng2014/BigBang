package com.qunar.work;

import com.google.common.collect.Lists;
import com.qunar.commonutil.FileUtil;
import com.qunar.mobile.innovation.histories.UserHistoryInfo;
import com.qunar.mobile.innovation.ticket.data.TicketOrderLog;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zhipengwu on 17-2-10.
 */
public class TicketOrderFilter {
    public static void main(String[] args) {
        String filename="/home/zhipengwu/secureCRT/ticket_order_offline_20170223.gz";
        int countNullOrderid=0;
        List<String> orderList= Lists.newArrayList();
        try {
            Scanner gzFileScaner = FileUtil.getGzFileScaner(filename);
            while (gzFileScaner.hasNext()){
                String line =gzFileScaner.nextLine();
                TicketOrderLog ticketOrderLog = UserHistoryInfo.GSON.fromJson(line, TicketOrderLog.class);
                if (ticketOrderLog!=null&&ticketOrderLog.orderId!=null){
                    String orderId=ticketOrderLog.orderId;
                    orderList.add(orderId);
                }else{
                    countNullOrderid++;
                    System.out.println(line);
                }
            }
            FileUtil.writeFile(orderList,"/home/zhipengwu/secureCRT/output/ticket_order_offline_20170223.txt");
            System.out.println("#############: countNullOrderid= "+countNullOrderid);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
