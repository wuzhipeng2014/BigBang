package com.qunar.StatisticTask;

import com.clearspring.analytics.util.Lists;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.qunar.StatisticTask.bean.cainixihuan.CParamsBean;
import com.qunar.StatisticTask.bean.cainixihuan.CainixihuanBean;
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
import java.util.List;
import java.util.Set;

/**
 * Created by zhipengwu on 17-6-28. 统计指定版本号的猜你喜欢 的 pv uv, 分clickType 统计
 */
public class Cainixihuan extends Configured implements Tool {
    public static class CainixihuanMapper extends Mapper<Object, Text, Text, Text> {

        public static List<String> targetVidList = Lists.newArrayList();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            targetVidList.add("60001169");
            targetVidList.add("80011136");
            targetVidList.add("80011137");
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (!Strings.isNullOrEmpty(line)) {
                CainixihuanBean cainixihuanBean = CainixihuanBean.getInstanceFromLog(line);
                if (cainixihuanBean != null) {
                    CParamsBean cparamsBean = cainixihuanBean.cParam;
                    if (cparamsBean == null) {
                        cparamsBean = cainixihuanBean.cParams;
                    }
                    if (cparamsBean != null && targetVidList.contains(cparamsBean.vid)) {
                        String clickType = Strings.isNullOrEmpty(cainixihuanBean.clickType) ? "null"
                                : cainixihuanBean.clickType;
                        String gid = cparamsBean.gid == null ? "null" : cparamsBean.gid;
                        String outputKey = String.format("%s\t%s", clickType, cparamsBean.vid);
                        context.write(new Text(outputKey), new Text(gid));
                        outputKey = String.format("%s\t%s", "All", cparamsBean.vid);
                        context.write(new Text(outputKey), new Text(gid));
                    }
                }

            }
        }
    }

    public static class CainixihuanReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            Set<String> gidSet = Sets.newHashSet();
            int countClickPv = 0;
            for (Text value : values) {
                String gid = value.toString();
                gidSet.add(gid.toUpperCase());
                countClickPv++;
            }
            String outputValue = String.format("%s\t%s", countClickPv, gidSet.size());
            context.write(key, new Text(outputValue));
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
        job.setJarByClass(Cainixihuan.class);
        job.setMapperClass(CainixihuanMapper.class);
        job.setReducerClass(CainixihuanReducer.class);
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
            status = ToolRunner.run(new Cainixihuan(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
