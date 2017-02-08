package com.qunar.work;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qunar.commonutil.FileUtil;
import com.qunar.work.bean.EtlClientBean;

/**
 * Created by zhipengwu on 17-1-23.
 */
public class etl_client {
    public static void main(String[] args) {
        // String line =
        // "ZHOU_BIAN_YOU_mht6j_START####{\"gid\":\"001A9007-20C5-31A7-EA82-A8CE49B296EE\",\"vid\":\"60001156\",\"cid\":\"C1033\",\"uid\":\"861545031027131\",\"cuid\":\"861545031027131\",\"pid\":\"10010\",\"username\":\"jlyh7633\",\"dt\":\"2017-01-21\",\"clickPos\":\"1\",\"clickTime\":\"20170121155456557\",\"clickType\":\"middlePageEntrance\",\"itemName\":\"晚出发10点出发，北京八达岭长城纯玩一日游，不早起，不看陵，不进店\",\"poiCity\":\"北京\",\"productTag\":\"一日游\",\"productType\":\"dujia\",\"requestId\":\"001A9007-20C5-31A7-EA82-A8CE49B296EE_1484985291318\",\"selectCity\":\"北京\",\"tab\":\"全部\"}";

        Gson gson = new Gson();

        String baseDir = "/home/zhipengwu/secureCRT/";
//        String filename = "ZHOUBIANYOUmht6jSTART_20170119";
//        String filename = "RECOMMENDCLICKYmVmYSTART_20170119";
        String filename = "HOMEPAGEMODULESHOWbn39saSTART_20170119";
        String inputFilename = baseDir + filename + ".gz";
        String outputFilename = baseDir +"etl_client/"+ filename + ".txt";
        Scanner gzFileScaner = null;
        try {
            gzFileScaner = FileUtil.getGzFileScaner(inputFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = Lists.newArrayList();
        int countExpLine = 0;
        while (gzFileScaner.hasNextLine()) {
            String s = gzFileScaner.nextLine();
            int openBraceIndex = s.indexOf('{');
            String substring = s.substring(openBraceIndex);
            EtlClientBean etlClientBean = gson.fromJson(substring, EtlClientBean.class);
            if (etlClientBean != null && etlClientBean.getCuid() != null && etlClientBean.getVid() != null) {
                if (etlClientBean.getVid().startsWith("80")) {
                    list.add(etlClientBean.getCuid());
                    if (list.size() > 1024) {
                        FileUtil.writeFile(list, outputFilename);
                        list.clear();
                    }
                }

            } else {
                countExpLine++;
            }

        }
        FileUtil.writeFile(list, outputFilename);
        list.clear();

        System.out.println("######countExpLine=" + countExpLine);

    }
}
