package com.qunar.other;

import com.qunar.mobile.innovation.histories.UserHistoryInfo;
import com.qunar.mobile.innovation.ticket.data.TicketOrderLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.Lz4Codec;
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
 * Created by zhipengwu on 17-1-6.
 */
public class TicketOrderFilter extends Configured implements Tool {

    public static class TicketOrderFilterMapper extends Mapper<Object, Text, Text, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line =value.toString();
            TicketOrderLog ticketOrderLog = UserHistoryInfo.GSON.fromJson(line, TicketOrderLog.class);
            if (ticketOrderLog!=null&&ticketOrderLog.gid!=null&&ticketOrderLog.orderId!=null){
                if (ticketOrderLog.gid!=null) {
                    context.write(new Text(ticketOrderLog.gid), new Text(ticketOrderLog.orderId));
                }else {
                    context.write(new Text(""), new Text(ticketOrderLog.orderId));

                }

            }
        }
    }


    public static class TicketOrderFilterReducer extends Reducer<Text, Text, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value:values){
                context.write( NullWritable.get(),value);
            }
        }
    }

        @Override
    public int run(String[] strings) throws Exception {
        if (strings.length != 2) {
            System.err.println("./xxxx <input1> <hotdoglog> <accesslog> <output>");
            System.exit(1);
        }
        String inputPath = strings[0];
        String outputpath = strings[1];
        int numReduceTasks = 8;
        Configuration conf = this.getConf();
        conf.set("mapred.job.queue.name", "wirelessdev");
        conf.set("mapreduce.map.memory.mb", "4096");
        conf.set("mapred.child.map.java.opts", "-Xmx4096m");
        conf.set("mapreduce.map.java.opts", "-Xmx4096m");
        conf.set("mapreduce.reduce.memory.mb", "8192");
        conf.set("mapred.child.reduce.java.opts", "-Xmx8192m");
        conf.set("mapreduce.reduce.java.opts", "-Xmx8192m");
        conf.set("mapred.job.priority", JobPriority.VERY_HIGH.name());
        conf.setBoolean("mapred.compress.map.output", true);
        conf.setClass("mapred.map.output.compression.codec", Lz4Codec.class, CompressionCodec.class);
        conf.setBoolean("mapred.output.compress", true);
        conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        Job job = Job.getInstance(conf);
        job.setJobName("VacationAddFavJob.zhipeng.wu");

        job.setJarByClass(TicketOrderFilter.class);

        job.setMapperClass(TicketOrderFilterMapper.class);
        job.setReducerClass(TicketOrderFilterReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(8);

        FileInputFormat.setInputPaths(job, inputPath.toString());
        FileOutputFormat.setOutputPath(job, new Path(outputpath));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new TicketOrderFilter(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
