package com.qunar.other;

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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据关键字过滤日志
 *
 * Created by zhipengwu on 16-11-22.
 */
public class UserBehaviorFilter extends Configured implements Tool {

    public static class UserBehaviorFilterMapper extends Mapper<Object, Text, Text, Text> {
        public String targetKey;
        public Pattern pattern;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            Configuration conf = context.getConfiguration();
            targetKey=conf.get("target");
            System.out.println("####################target: "+targetKey);

//            targetKey="DADC3DB9-8159-20DE-3681-F634CD7E110E";
            String regex="(.*?)(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{1,3})(.*?)";
            pattern = Pattern.compile(regex);

        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line =value.toString();


            //时间字符串例子: 2016-11-18 21:45:36,583
            String logtime="";
            System.out.println("####################target: "+targetKey);
            if (line!=null && line.contains(targetKey)){
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    logtime = matcher.group(2);
                }
                context.write(new Text(logtime),new Text(line));
            }
        }
    }

    public static class UserBehaviorFilterReducer extends Reducer<Text, Text, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            for(Text value:values){
                context.write(NullWritable.get(),value);
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception
    {

        if (args.length != 4) {
            System.err.println("./run <input> <output> <reducetasknumber>");
            System.exit(1);
        }
        String inputPaths = args[0];
        String outputPath = args[1];
        int numReduceTasks = Integer.parseInt(args[2]);
        String targetKey= args[3]; //要提取的用户gid
        System.out.println("##############:targetKey: "+targetKey);

        Configuration conf = this.getConf();
        conf.set("mapred.job.queue.name", "wirelessdev");
        conf.set("mapreduce.map.memory.mb", "8192");
        conf.set("mapreduce.reduce.memory.mb", "8192");
        // conf.set("mapred.child.reduce.java.opts", "-Xmx8192m");
        // conf.set("mapreduce.reduce.java.opts", "-Xmx8192m");
        conf.set("mapred.job.priority", JobPriority.VERY_HIGH.name());
        conf.setBoolean("mapred.output.compress", true);
        conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        //传递变量
        conf.setStrings("target", targetKey);

        Job job = Job.getInstance(conf);
        job.setJobName("SuggestionList_KunLinZu");
        job.setJarByClass(UserBehaviorFilter.class);
        job.setMapperClass(UserBehaviorFilterMapper.class);
        job.setReducerClass(UserBehaviorFilterReducer.class);
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


    /**
     *
     * @param args
     * 输入路径
     * 输出路径
     * reduce个数
     * 要包含的目标关键字
     */
    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new UserBehaviorFilter(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
