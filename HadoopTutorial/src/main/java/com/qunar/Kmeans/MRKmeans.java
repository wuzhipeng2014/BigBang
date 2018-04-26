package com.qunar.Kmeans;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;

/**
 * Created by zhipengwu on 18-2-28.
 * 利用mapreduce实现kmeans算法
 */
public class MRKmeans  extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        return 0;
    }
//    public static class MRCommonModelMapper extends Mapper<Object, Text, Text, Text> {
//
//        //中心集合
//        ArrayList<ArrayList<Double>> centers = null;
//        //用k个中心
//        int k = 0;
//
//
//        @Override
//        protected void setup(Context context) throws IOException, InterruptedException {
//            super.setup(context);
//        }
//
//
//        @Override
//        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
//            super.map(key, value, context);
//            //读取一行数据
//            ArrayList<Double> fileds = Utils.textToArray(value);
//            int sizeOfFileds = fileds.size();
//
//            double minDistance = 99999999;
//            int centerIndex = 0;
//
//            //依次取出k个中心点与当前读取的记录做计算
//            for(int i=0;i<k;i++){
//                double currentDistance = 0;
//                for(int j=0;j<sizeOfFileds;j++){
//                    double centerPoint = Math.abs(centers.get(i).get(j));
//                    double filed = Math.abs(fileds.get(j));
//                    currentDistance += Math.pow((centerPoint - filed) / (centerPoint + filed), 2);
//                }
//                //循环找出距离该记录最接近的中心点的ID
//                if(currentDistance<minDistance){
//                    minDistance = currentDistance;
//                    centerIndex = i;
//                }
//            }
//            //以中心点为Key 将记录原样输出
//            context.write(new IntWritable(centerIndex+1), value);
//
//        }
//    }
//
//    public static class MRCommonModelReducer extends Reducer<Text, Text, NullWritable, Text> {
//        @Override
//        protected void reduce(Text key, Iterable<Text> values, Context context)
//                throws IOException, InterruptedException {
//            super.reduce(key, values, context);
//            ArrayList<ArrayList<Double>> filedsList = new ArrayList<ArrayList<Double>>();
//
//            //依次读取记录集，每行为一个ArrayList<Double>
//            for(Iterator<Text> it =value.iterator();it.hasNext();){
//                ArrayList<Double> tempList = Utils.textToArray(it.next());
//                filedsList.add(tempList);
//            }
//
//            //计算新的中心
//            //每行的元素个数
//            int filedSize = filedsList.get(0).size();
//            double[] avg = new double[filedSize];
//            for(int i=0;i<filedSize;i++){
//                //求没列的平均值
//                double sum = 0;
//                int size = filedsList.size();
//                for(int j=0;j<size;j++){
//                    sum += filedsList.get(j).get(i);
//                }
//                avg[i] = sum / size;
//            }
//            context.write(new Text("") , new Text(Arrays.toString(avg).replace("[", "").replace("]", "")));
//
//        }
//    }
//
//    public int run(String[] args) throws Exception {
//        if (args.length != 3) {
//            System.err.println("./run <input> <output> <reducetasknumber>");
//            System.exit(1);
//        }
//        String inputPaths = args[0];
//        String outputPath = args[1];
//        int numReduceTasks = Integer.parseInt(args[2]);
//
//        Configuration conf = this.getConf();
//        conf.set("mapred.job.queue.name", "wirelessdev");
//        conf.set("mapreduce.map.memory.mb", "8192");
//        conf.set("mapreduce.reduce.memory.mb", "8192");
//        conf.set("mapred.child.reduce.java.opts", "-Xmx8192m");
//        conf.set("mapreduce.reduce.java.opts", "-Xmx8192m");
//        conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);
//        conf.set("mapred.job.priority", JobPriority.VERY_HIGH.name());
//        conf.setBoolean("mapred.output.compress", true);
//        conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
//        // 传递变量
//        // conf.setStrings("target", targetKey);
//
//        Job job = Job.getInstance(conf);
//        job.setJobName("SuggestionList_ZhipengWu");
//        job.setJarByClass(MRKmeans.class);
//        job.setMapperClass(MRCommonModelMapper.class);
//        job.setReducerClass(MRCommonModelReducer.class);
//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(Text.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputKeyClass(NullWritable.class);
//        job.setOutputValueClass(Text.class);
//
//        FileInputFormat.setInputPaths(job, inputPaths);
//        FileOutputFormat.setOutputPath(job, new Path(outputPath));
//        job.setNumReduceTasks(numReduceTasks);
//
//        boolean success = job.waitForCompletion(true);
//        return success ? 0 : 1;
//    }
//
//    public static void main(String[] args) {
//        int status = 0;
//
//
//        try {
//            status = ToolRunner.run(new MRKmeans(), args);
//            System.out.println(" 第 " + ++count + " 次计算 ");
//            while (true) {
//                if (Utils.compareCenters(centerPath, newCenterPath)) {
//                    status = ToolRunner.run(new MRKmeans(), args);
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.exit(status);
//    }
}
