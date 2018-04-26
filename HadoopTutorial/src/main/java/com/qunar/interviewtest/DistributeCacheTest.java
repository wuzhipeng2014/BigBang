package com.qunar.interviewtest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Created by zhipengwu on 17-2-7. MR中使用分布式缓存实现大文件共享
 *
 * 运行方式:
 *  sudo -uwirelessdev hadoop jar /home/q/zhipeng.wu/work/tmpData/HadoopTutorial-jar-with-dependencies.jar com.qunar.test.DistributeCacheTest  zhipeng.wu/testdata/train_order_20170108  zhipeng.wu/testdata/train_order_20170108_copy 8 hdfs://qunarcluster/user/wirelessdev/zhipeng.wu/testdata/input.txt
 */
public class DistributeCacheTest extends Configured implements Tool {

    public static class DistributeCacheTestMapper extends Mapper<Object, Text, Text, Text> {

        Set<String> cacheSet = Sets.newHashSet();
        private Configuration conf;
        List<String> cacheList;

        // 缓存文件读取
        public List<String> readFile(String fileName) {
            List<String> lists = Lists.newArrayList();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(fileName));
                String line = null;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        lists.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return lists;
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            cacheList = Lists.newArrayList();
            conf = context.getConfiguration();
            if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) {
                URI[] cacheFiles = Job.getInstance(conf).getCacheFiles();
                for (URI uri : cacheFiles) {
                    Path path = new Path(uri.getPath());
                    String filename = path.getName();
                    readFile(filename);
                    cacheList.addAll(readFile(filename));
                }

                for (String line : cacheList) {
                    Counter counter = context.getCounter("cacheFile", line);
                    counter.setValue(line.length());
                }

                // String path = context.getCacheFiles()[0].getPath();
                // File itermOccurrenceMatrix = new File(path);
                // FileReader fileReader = new FileReader(itermOccurrenceMatrix);
                // BufferedReader bufferedReader = new BufferedReader(fileReader);
                // String s;
                // while ((s = bufferedReader.readLine()) != null) {
                // //TODO:读取每行内容进行相关的操作
                // cacheSet.add(s);
                // }
                // bufferedReader.close();
                // fileReader.close();
            }
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            int half = line.length() / 2;
            int subIndex = half < 10 ? half : 10;
            context.write(new Text(line.substring(0, subIndex)), value);

        }
    }

    public static class DistributeCacheTestReducer extends Reducer<Text, Text, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            super.reduce(key, values, context);
            for (Text value : values) {
                context.write(NullWritable.get(), value);
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.println("./run <input> <output> <reducetasknumber>");
            System.exit(1);
        }
        String inputPaths = args[0];
        String outputPath = args[1];
        String cacheFile = args[3];
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

        Job job = Job.getInstance(conf);
        job.setJobName("SuggestionList_ZhipengWu");
        job.setJarByClass(DistributeCacheTest.class);
        job.setMapperClass(DistributeCacheTest.DistributeCacheTestMapper.class);
        job.setReducerClass(DistributeCacheTest.DistributeCacheTestReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        // 将hdfs上的文件加入分布式缓存
//        job.addCacheFile(new URI("hdfs://qunarcluster/user/wirelessdev/zhipeng.wu/testdata/input.txt.gz"));
        job.addCacheFile(new URI(cacheFile));

        FileInputFormat.setInputPaths(job, inputPaths);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.setNumReduceTasks(numReduceTasks);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new DistributeCacheTest(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
