package com.qunar.work;

import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.commonutil.FileUtil;
import com.qunar.mobile.innovation.util.ParseString;
import com.qunar.mobile.innovation.vacation.data.VacationSearchClick;

/**
 * Created by zhipengwu on 17-1-16.
 */
public class VacationClickDebug {

    public static Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss.SSS").serializeSpecialFloatingPointValues().create();

    public static void main(String[] args) {
        String inputFile="/home/zhipengwu/secureCRT/vacation_click_20170115.gz";
        int count=0;
        int count2=0;
        int i=0;
        try {
            Scanner gzFileScaner = FileUtil.getGzFileScaner(inputFile);
            while (gzFileScaner.hasNextLine()){
                String line =gzFileScaner.nextLine();
                VacationSearchClick vclk = gson.fromJson(line, VacationSearchClick.class);
                String gid = vclk.gid;
                if (gid == null) {
                    gid = "";
                }
                String identifier = ParseString.normalizeXid(gid);
                if (ParseString.isValidIdentifier(identifier) == true) {
                    VacationSearchClick vacationSearchClick = gson.fromJson(line, VacationSearchClick.class);
                    count++;
                }else{
                    System.out.println(String.format("%s,  %s",i++,gid));
                    count2++;
                    if (count2%100==0){
                        System.out.println();
                    }
                }
            }

            System.out.println("########count   "+count);
            System.out.println("########count2   "+count2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
