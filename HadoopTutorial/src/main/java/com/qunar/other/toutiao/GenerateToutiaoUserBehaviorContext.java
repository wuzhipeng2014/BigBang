package com.qunar.other.toutiao;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.other.toutiao.bean.ToutiaoClickLog;
import com.qunar.other.toutiao.bean.ToutiaoShowLog;
import com.qunar.other.toutiao.bean.ToutiaoUserInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by zhipengwu on 17-11-7. 将头条用户的展示 点击 用户信息关联
 */
public class GenerateToutiaoUserBehaviorContext extends Configured implements Tool {
    public static Gson gson = new GsonBuilder().create();
   public static Logger logger=LoggerFactory.getLogger(GenerateToutiaoUserBehaviorContext.class);
    public static class MRCommonModelMapper extends Mapper<Object, Text, Text, Text> {

        String fileInputPath = "";

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            fileInputPath = ((FileSplit) context.getInputSplit()).getPath().toString();

        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (Strings.isNullOrEmpty(line)) {
                return;
            }
            String json=line.substring(line.indexOf("{"));
            //用户信息
            if (fileInputPath.contains("marmot_running/mr-in/toutiao_userInfo_address/all")) {
                ToutiaoUserInfo toutiaoUserInfo = gson.fromJson(json, ToutiaoUserInfo.class);
                if (toutiaoUserInfo != null && !Strings.isNullOrEmpty(toutiaoUserInfo.md5KeyId)) {
                    toutiaoUserInfo.className = "ToutiaoUserInfo";
                    context.write(new Text(toutiaoUserInfo.md5KeyId), new Text(line));
                }

            } else if (fileInputPath.contains("log/ads/show")) {
                ToutiaoShowLog toutiaoShowLog = gson.fromJson(json, ToutiaoShowLog.class);
                if (toutiaoShowLog != null && !Strings.isNullOrEmpty(toutiaoShowLog.md5_device_id)) {
                    toutiaoShowLog.className = "ToutiaoShowLog";
                    context.write(new Text(toutiaoShowLog.md5_device_id), new Text(line));
                }
            } else if (fileInputPath.contains("log/ads/click")) {
                ToutiaoClickLog toutiaoClickLog = gson.fromJson(json, ToutiaoClickLog.class);
                if (toutiaoClickLog != null && !Strings.isNullOrEmpty(toutiaoClickLog.md5_device_id)) {
                    toutiaoClickLog.className = "ToutiaoClickLog";
                    context.write(new Text(toutiaoClickLog.md5_device_id), new Text(line));
                }
            }

        }
    }

    public static class MRCommonModelReducer extends Reducer<Text, Text, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            Map<String, String> map = Maps.newTreeMap();
            boolean isDump = false;
            boolean isShow = false;
            int userInfoNum=0;
            String md5KeyId="";
            for (Text value : values) {
                String line = value.toString();
                if (!Strings.isNullOrEmpty(line)) {
                    try {
                        String jsonContent = line.substring(line.indexOf("{"));

                        if (line.contains("INFO click")) {
                            isDump = true;
                        }
                        if (line.contains("INFO show")){

                            ToutiaoShowLog toutiaoShowLog = gson.fromJson(jsonContent, ToutiaoShowLog.class);
                            if (toutiaoShowLog!=null&&toutiaoShowLog.adType.equals("new")){
                                isShow=true;
                            }
                        }else {
                            String time = line.substring(1, 20);
                            if (!Strings.isNullOrEmpty(time)) {
//                            map.put(time, line);
                                map.put(time, null);
                                userInfoNum++;
                                if (userInfoNum>10000){
                                    break;
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error("reduce端发生错误"+line,e);
                    }
                }
            }
            if (isDump==false&&isShow==true){
                Object[] objects = map.keySet().toArray();
                String s = Arrays.toString(objects);
                context.write(NullWritable.get(),new Text(String.format("%s\t%s\t%s",key.toString(),userInfoNum,s)));
//                for (String k:map.keySet()){
////                    context.write(NullWritable.get(),new Text(map.get(k)));
//                    context.write(NullWritable.get(),new Text(String.format("%s\t%s\t%s\t%s",key.toString(),k,userInfoNum,s)));
//                }
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("./run <input> <output> <reducetasknumber>");
            System.exit(1);
        }
        String inputPaths = args[0];
        String outputPath = args[1];
        int numReduceTasks = Integer.parseInt(args[2]);

        Configuration conf = this.getConf();
        conf.set("mapred.job.queue.name", "wirelessdev");
        conf.set("mapreduce.map.memory.mb", "16000");
        conf.set("mapreduce.reduce.memory.mb", "16000");
        conf.set("mapred.child.reduce.java.opts", "-Xmx16000m");
        conf.set("mapreduce.reduce.java.opts", "-Xmx16000m");
        conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);
        conf.set("mapred.job.priority", JobPriority.VERY_HIGH.name());
        conf.setBoolean("mapred.output.compress", true);
        conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        // 传递变量
        // conf.setStrings("target", targetKey);

        Job job = Job.getInstance(conf);
        job.setJobName("SuggestionList_ZhipengWu");
        job.setJarByClass(GenerateToutiaoUserBehaviorContext.class);
        job.setMapperClass(MRCommonModelMapper.class);
        job.setReducerClass(MRCommonModelReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, inputPaths);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.setNumReduceTasks(numReduceTasks);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new GenerateToutiaoUserBehaviorContext(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
