package com.qunar.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.elasticsearch.common.Strings;

import java.io.IOException;

/**
 * Created by zhipengwu on 17-12-21. 利用mapreduce 实现全局排序
 */
public class GlobalRanking extends Configured implements Tool {
    public static class MRCommonModelMapper extends Mapper<Object, Text, LongWritable, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString();
            if (!Strings.isNullOrEmpty(line)){
                String[] split = line.split("\t");
                if (split.length==3){
                    if (!split[2].equals("null")) {
                        Long similarity = ((Double)(Double.valueOf(split[2])*10000)).longValue();
                        context.write(new LongWritable(similarity) ,new Text(line));
                    }
                }
            }
        }
    }

    public static class MRCommonModelReducer extends Reducer<LongWritable, Text, NullWritable, Text> {
        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            super.reduce(key, values, context);
            for(Text value:values){
                context.write(NullWritable.get(),new Text(value));
            }
        }
    }

    public int run(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println("./run <input> <output> <reducetasknumber> <paritionPath>");
            System.exit(1);
        }
        String inputPaths = args[0];
        String outputPath = args[1];
        int numReduceTasks = Integer.parseInt(args[2]);
        String paritionPath=args[3];

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
        job.setJarByClass(GlobalRanking.class);
        job.setMapperClass(MRCommonModelMapper.class);
        job.setReducerClass(MRCommonModelReducer.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, inputPaths);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.setNumReduceTasks(numReduceTasks);

        //todo 全局排序
        TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), new Path(paritionPath));
        InputSampler.Sampler<Text, Text> sampler = new InputSampler.RandomSampler(0.01, 1000, 100);
        InputSampler.writePartitionFile(job, sampler);


        job.setPartitionerClass(TotalOrderPartitioner.class);

       



        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new GlobalRanking(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
