package com.qunar.UserBehaviorFilter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
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
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhipengwu on 17-1-22. 功能:过滤出包含制定关键字的行并输出到制定目录 highLight1: 一次扫描可以过滤出多个关键字 highLight2: 支持正则表达式过滤
 * hightLight3: 支持keywords计数(keywords出现的总次数,一行 出现多次,则记录多次)
 */
public class KeyWordsFilter extends Configured implements Tool {

    public static class KeyWordsFilterMapper extends Mapper<Object, Text, Text, Text> {
        String targetKeys;
        String separator;
        Iterable<String> targetKeysList;
        String outputBaseDir;
        private MultipleOutputs outputs;

        public static List<Pattern> PATTERN_LIST = Lists.newArrayList(); // 存储待匹配的pattern,与PREFIX_SUFFIX_LIST相对应

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);

            Configuration conf = context.getConfiguration();
            targetKeys = conf.get("targetKeys");
            String targetKeysSeparator = conf.get("targetKeysSeparator");
            outputs = new MultipleOutputs<Text, Text>(context);
            outputBaseDir = conf.get("outputBaseDir");
            targetKeysList = Splitter.on(targetKeysSeparator).trimResults().omitEmptyStrings().split(targetKeys);
            for (String key : targetKeysList) {
                PATTERN_LIST.add(Pattern.compile(key));
            }
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            outputs = new MultipleOutputs<Text, Text>(context);

            if (line != null) {
                for (int i = 0; i < PATTERN_LIST.size(); i++) {
                    Pattern pattern = PATTERN_LIST.get(i);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String matchedKey = PATTERN_LIST.get(i).toString();
                        String prefix = matchedKey;
                        if (matchedKey.length() > 20) {
                            // todo 考虑待匹配的key关键字前20个字符相同的情况
                            // todo 可以考虑求出这组字符串的最长公共前缀^_^
                            prefix = matchedKey.substring(0, 20);
                        }
                        int matchTimes = matcher.groupCount();
                        context.getCounter("TargetKeyCounters", matchedKey).increment(matchTimes);
                        outputs.write(NullWritable.get(), value, String.format("%s/%s", prefix, prefix));
                    }
                }

            }
        }
    }

    public static class KeyWordsFilterReducer extends Reducer<Text, Text, NullWritable, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        InputArgs inputArgs = InputArgsParser.parseInputArgs(args);
        int numReduceTasks = inputArgs.reduceNumbers;
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
        job.setJarByClass(KeyWordsFilter.class);

        job.setMapperClass(KeyWordsFilterMapper.class);
        job.setReducerClass(KeyWordsFilterReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(numReduceTasks);

        // 禁止输出目录生成part-r-00000或者part-m-00000的空文件
        LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);

        // 判断输出路径是否存在,如果存在则删除
        Path path = new Path(inputArgs.outputPath);
        FileSystem fileSystem = path.getFileSystem(conf);
        if (fileSystem.isDirectory(path)) {
            fileSystem.delete(path, true);
        }

        List<String> targetKeysList = Lists.newArrayList();
        // 多路径输出
        int len = args.length;
        if (len > 3) {
            for (int i = 2; i < len; i++) {
                targetKeysList.add(args[i]);
                String input = String.format("%s/%s", inputArgs.outputPath, args[i]);
                MultipleOutputs.addNamedOutput(job, input, TextOutputFormat.class, Text.class, Text.class);
            }
        }
        Joiner joiner = Joiner.on(inputArgs.separator).skipNulls();
        String targetKeys = joiner.join(targetKeysList);

        // 传递变量
        conf.setStrings("targetKeys", targetKeys);
        conf.setStrings("targetKeysSeparator", inputArgs.separator);
        conf.setStrings("outputBaseDir", inputArgs.outputPath);

        FileInputFormat.setInputPaths(job, inputArgs.inputPath);
        FileOutputFormat.setOutputPath(job, new Path(inputArgs.outputPath));
        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    /**
     *
     * @param args 1. 输入路径 2. 输出路径 3. reduce个数 4. 要过滤的关键字(可以为多个)
     */
    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new KeyWordsFilter(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
