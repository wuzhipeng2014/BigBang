package com.qunar.StatisticTask;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.StatisticTask.bean.DiscoverBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * Created by zhipengwu on 17-4-25.
 */
public class DiscoveryChannel extends Configured implements Tool {
    public static class DiscoveryChannelMapper extends Mapper<Object, Text, Text, Text> {
        public static Gson gson=new GsonBuilder().create();


        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split("\t");
            if (split != null && split.length > 21) {
                String logdate = split[0];
                String valdate = split[6];
                String action=split[8];
                if (!Strings.isNullOrEmpty(action)&&action.contains("hy:set")&&action.contains("\"sid\"：\"发现频道\"")){
                    int index = action.indexOf("{");
                    DiscoverBean discoverBean = gson.fromJson(action.substring(index), DiscoverBean.class);
                    if (discoverBean!=null&&discoverBean.data.sid.equalsIgnoreCase("发现频道")){
                        String keyFormat=String.format("%s\t%s",logdate,valdate);
                        context.write(new Text(keyFormat),new Text("1"));
                    }
                }
            }
        }
    }

    public static class DiscoveryChannelReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            int count=0;
            for (Text value:values){
                count++;
            }
            context.write(key,new Text(String.valueOf(count)));
        }
    }

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
        job.setJarByClass(DiscoveryChannel.class);
        job.setMapperClass(DiscoveryChannelMapper.class);
        job.setReducerClass(DiscoveryChannelReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputKeyClass(Text.class);
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
            status = ToolRunner.run(new DiscoveryChannel(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
