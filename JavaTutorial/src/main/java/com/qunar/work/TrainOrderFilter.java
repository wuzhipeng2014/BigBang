package com.qunar.work;

import com.qunar.commonutil.FileUtil;
import com.qunar.mobile.innovation.behavior.train.TrainOrderBehavior;
import com.qunar.mobile.innovation.histories.UserHistoryInfo;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by zhipengwu on 17-1-19.
 */
public class TrainOrderFilter {

    public static void main(String[] args) {
        String inputBaseDir = "/home/zhipengwu/secureCRT/";
        String filename="train_order_20170212.gz";
        try {
            Scanner gzFileScaner = FileUtil.getGzFileScaner(inputBaseDir + filename);
            while (gzFileScaner.hasNextLine()){
                String s = gzFileScaner.nextLine();
                TrainOrderBehavior trainOrderBehavior = UserHistoryInfo.GSON.fromJson(s, TrainOrderBehavior.class);
                System.out.println(trainOrderBehavior.orderId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
