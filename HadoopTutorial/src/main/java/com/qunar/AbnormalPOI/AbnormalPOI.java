package com.qunar.AbnormalPOI;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qunar.AbnormalPOI.bean.MyTime;
import com.qunar.AbnormalPOI.bean.UserPOI;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.Lz4Codec;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * 用户行为数据异常poi统计(短时间内poi发生变化的用户)
 * Created by zhipengwu on 16-11-21.
 */
public class AbnormalPOI extends Configured implements Tool {

    public static class AbnormalPOIMapper extends Mapper<Object, Text, Text, Text> {
        public Gson gson;
        public String defaultValue;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            gson = new GsonBuilder().create();
            defaultValue = "";

        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            int openBraceIndex = line.indexOf("{");
            String jsonLine = line.substring(openBraceIndex);
            Map<String, Object> map = gson.fromJson(jsonLine, new TypeToken<Map<String, Object>>() {
            }.getType());
            String gid = map.get("identitifier").toString();
            String businessType = getOrDefault(map, "businessType", defaultValue);
            Map<String, Object> behavior = (Map<String, Object>) map.get("behavior");
            String actionTime = getOrDefault(behavior, "actionTime", defaultValue);
            String userCityName = getOrDefault(behavior, "userCityName", defaultValue);

            UserPOI userPOI = new UserPOI(gid, actionTime, userCityName, businessType);

            if (StringUtils.isNotEmpty(gid) && userPOI.getUserCityName() != null
                    && userPOI.getUserCityName().length() > 0) {
                context.write(new Text(gid), new Text(gson.toJson(userPOI)));
            }
        }

        public String getOrDefault(Map<String, Object> map, String key, String defaultValue) {
            if (null != map) {
                if (map.containsKey(key)) {
                    return map.get(key).toString();
                }
            }
            return defaultValue;

        }

    }

    public static class AbnormalPOIReducer extends Reducer<Text, Text, Text, Text> {

        public Gson gson;
        int countTotal;
        public static Counter ct = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            gson = new GsonBuilder().create();
            countTotal = 0;
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {

            List<UserPOI> userPOIList = Lists.newArrayList();
            countTotal++;
            ct = context.getCounter("countLine", "countAll");
            ct.increment(1);
            for (Text value : values) {
                String line = value.toString();
                UserPOI userPOI = gson.fromJson(line, UserPOI.class);
                userPOIList.add(userPOI);
            }
            // 对list按时间顺序排序
            Comparator<UserPOI> byActionTime = new Comparator<UserPOI>() {
                public int compare(UserPOI o1, UserPOI o2) {
                    return o1.getActionTime().compareTo(o2.getActionTime());
                }
            };
            Collections.sort(userPOIList, byActionTime);

            // 判断是否存在POI定位错误
            boolean b = judgeAbnormalPOI(context, userPOIList);
            if (!b) {
                // context.write(NullWritable.get(), key);
            }

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);

            // context.write(NullWritable.get(),new Text(String.valueOf(countTotal)));
            context.write(new Text("countAll"),
                    new Text(String.valueOf(context.getCounter("countLine", "countAll").getValue())));

        }

        /**
         * 判断用户行为信息中是否存在POI异常的项
         *
         * @param userPOIList
         * @return
         */
        private boolean judgeAbnormalPOI(Context context, List<UserPOI> userPOIList) {
            boolean result = true;
            MyTime timei = new MyTime();
            MyTime timej = new MyTime();
            for (int i = 0; i < userPOIList.size(); i++) {
                UserPOI userPOI = userPOIList.get(i);
                Preconditions.checkNotNull(userPOI, "UserPOI对象为空; gid= " + userPOI.getGid());
                Preconditions.checkNotNull(userPOI.getActionTime(), "actiontime为空; gid= " + userPOI.getGid());
                String actionTime = userPOI.getActionTime();
                timei.setMyTime(actionTime);
                String businessType = userPOI.getBusinessType();
                for (int j = i + 1; j < userPOIList.size(); j++) {
                    UserPOI userPOIj = userPOIList.get(j);
                    timej.setMyTime(userPOIj.getActionTime());
                    boolean sameTime = timei.isSameTime(timej);
                    if (sameTime) {
                        String userCityNamei = userPOI.getUserCityName();
                        String userCityNamej = userPOIj.getUserCityName();
                        boolean standardEqual = false;
                        // 城市标准化后
                        if (userCityNamei.contains(userCityNamej) || userCityNamej.contains(userCityNamei)) {
                            standardEqual = true;
                        }
                        if ((!userPOI.getUserCityName().equals(userPOIj.getUserCityName())) && !standardEqual) {
                            // 获取业务线名称
                            String businessTypej = userPOIj.getBusinessType();
                            Counter pairCount = context.getCounter("pairCount",
                                    String.format("%s -> %s", businessType, businessTypej));
                            pairCount.increment(1);
                            result = false;
                            try {
                                context.write(new Text(String.format("%s -> %s", businessType, businessTypej)),
                                        new Text(userPOI.getGid()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // return result;
                        }
                    } else {
                        break;
                    }
                }

            }
            return result;
        }

    }

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

        job.setJarByClass(AbnormalPOI.class);

        job.setMapperClass(AbnormalPOIMapper.class);
        job.setReducerClass(AbnormalPOIReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(numReduceTasks);

        FileInputFormat.setInputPaths(job, inputPath.toString());
        FileOutputFormat.setOutputPath(job, new Path(outputpath));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) {
        int status = 0;
        try {
            status = ToolRunner.run(new AbnormalPOI(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(status);
    }
}
