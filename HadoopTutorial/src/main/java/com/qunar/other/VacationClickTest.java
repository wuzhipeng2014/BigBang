package com.qunar.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qunar.mobile.innovation.util.ParseString;
import com.qunar.mobile.innovation.vacation.data.VacationSearchClick;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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
 * Created by zhipengwu on 17-1-16.
 */
public class VacationClickTest extends Configured implements Tool {

    public static Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss.SSS").serializeSpecialFloatingPointValues().create();


    public static class VacationClickTestMapper extends Mapper<Object, Text, Text, Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            VacationSearchClick vclk = gson.fromJson(line, VacationSearchClick.class);
            String gid = vclk.gid;
            if (gid == null) {
                gid = "";
            }
            String identifier = ParseString.normalizeXid(gid);
            if (ParseString.isValidIdentifier(identifier) == true) {
                context.write(new Text(identifier), new Text("clk:" + line));
            }
        }
    }


    public static class VacationClickTestReducer extends Reducer<Text, Text, Text, Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            for (Text val : values) {
                String value = val.toString();
                VacationSearchClick vacationSearchClick = gson.fromJson(value.substring(4), VacationSearchClick.class);

                context.write(new Text(gson.toJson(vacationSearchClick)), null);

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

        job.setJarByClass(VacationClickTest.class);

        job.setMapperClass(VacationClickTestMapper.class);
        job.setReducerClass(VacationClickTestReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, inputPath.toString());
        FileOutputFormat.setOutputPath(job, new Path(outputpath));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new VacationClickTest(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
