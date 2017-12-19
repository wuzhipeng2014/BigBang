package com.qunar.other.toutiao;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.other.toutiao.bean.ToutiaoUserInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.Counter;
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
import java.util.Map;

/**
 * Created by zhipengwu on 17-11-8. 头条每天使用男女用户比例统计
 */
public class ToutiaoUserGenderSta extends Configured implements Tool {

    public static Gson gson = new GsonBuilder().create();
    public static Logger logger = LoggerFactory.getLogger(GenerateToutiaoUserBehaviorContext.class);

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
            String json = line.substring(line.indexOf("{"));
            // 用户信息
            if (fileInputPath.contains("marmot_running/mr-in/toutiao_userInfo_address/all")) {
                ToutiaoUserInfo toutiaoUserInfo = gson.fromJson(json, ToutiaoUserInfo.class);
                if (toutiaoUserInfo != null && !Strings.isNullOrEmpty(toutiaoUserInfo.md5KeyId)) {
                    toutiaoUserInfo.className = "ToutiaoUserInfo";
                    context.write(new Text(toutiaoUserInfo.md5KeyId), new Text(toutiaoUserInfo.gender));
                }

            }
        }

    }


    public static class MRCommonModelReducer extends Reducer<Text, Text, NullWritable, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, String> map = Maps.newTreeMap();
        boolean isDump = false;
        for (Text value : values) {
            String line = value.toString();
            if (!Strings.isNullOrEmpty(line)) {
                Counter genderCounter = context.getCounter("GenderCounter", line);
                genderCounter.increment(1L);
                break;
            }
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
        conf.set("mapreduce.map.memory.mb", "8192");
        conf.set("mapreduce.reduce.memory.mb", "8192");
        conf.set("mapred.child.reduce.java.opts", "-Xmx8192m");
        conf.set("mapreduce.reduce.java.opts", "-Xmx8192m");
        conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);
        conf.set("mapred.job.priority", JobPriority.VERY_HIGH.name());
        conf.setBoolean("mapred.output.compress", true);
        conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        // 传递变量
        // conf.setStrings("target", targetKey);

        Job job = Job.getInstance(conf);
        job.setJobName("SuggestionList_ZhipengWu");
        job.setJarByClass(ToutiaoUserGenderSta.class);
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
            status = ToolRunner.run(new ToutiaoUserGenderSta(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}

