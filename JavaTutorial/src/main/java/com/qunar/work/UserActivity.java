package com.qunar.work;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.commonutil.FileUtil;
import com.qunar.commonutil.RegExpUtil;
import com.qunar.work.bean.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by zhipengwu on 16-12-15.
 */
public class UserActivity {

    public static final Gson gson= new GsonBuilder().create();
    public static final Pattern compile = Pattern.compile("\"gid\":\".*?\"");
    public static Logger logger = LoggerFactory.getLogger(UserActivity.class);
    public static void main(String[] args) {

//        if (args.length!=2){
//            System.exit(-1);
//        }
//        String inputFile=args[0];
//        String outputFile=args[1];
        String inputFile= "/home/zhipengwu/secureCRT/ZHOUBIANYOUmht6jSTART.gz";
                String outputFile="/home/zhipengwu/secureCRT/Uniq_ZHOUBIANYOUmht6jSTART.txt";
        filteringGids(inputFile,outputFile);


    }

    /**
     * 从输入文件中过滤出gid并写入输出文件
     * @param inputFile
     * @param outputFile
     */
    public static void  filteringGids(String inputFile, String outputFile){
        List<String> bufferList= Lists.newArrayList();
        try {
            Scanner gzFileScaner = FileUtil.getGzFileScaner(inputFile);
            if (gzFileScaner==null){
                logger.error("输入文件为空 "+inputFile);
                return;
            }
            String gid="";
            while (gzFileScaner.hasNextLine()){
                gid = filteringGid(gzFileScaner.nextLine());
                if (gid!=null&&gid.length()>0) {
                    bufferList.add(gid);
                }
                if (bufferList.size()>=1024) {
                    FileUtil.writeFile(bufferList, outputFile);
                    bufferList.clear();
                }
            }

            FileUtil.writeFile(bufferList, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从一行日志中抽出gid并返回
     * @param line
     * @return
     */
    public static String filteringGid(String line){
        int index=-1;
        String sourceLine=line;
        if (line!=null&&line.contains("{")&&line.contains("}")) {
            index= line.indexOf("{");
            if (index>0){
                line=line.substring(index);
            }
            try {
                UserBean userBean = gson.fromJson(line, UserBean.class);
                return userBean.getGid();
            }catch (Exception e){
                List<String> strings = RegExpUtil.MatchReg(compile, line);
                if (strings!=null&&strings.size()>0) {
                    String gidString=strings.get(0);
                    int colonIndex = gidString.indexOf(":");
                    if (colonIndex>0){
                        return gidString.substring(colonIndex+2,gidString.length()-1);
                    }
                }else {
                    System.out.println(line);
                    e.printStackTrace();
                    logger.error(line+"filteringGid执行失败,{} ",e);
                }
            }

        }
        return null;
    }


}
