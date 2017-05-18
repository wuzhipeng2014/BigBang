package com.qunar.MLLlib;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

/**
 * 使用逻辑回归拟合数据
 * Created by zhipengwu on 17-5-17.
 */
public class PredicationWithLogisticRegression {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaLogisticRegressionWithLBFGSExample");
        SparkContext sc = new SparkContext(conf);
        // $example on$
        String path = "data/mllib/sample_libsvm_data.txt";
//        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();
//
//        // Split initial RDD into two... [60% training data, 40% testing data].
//        JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] {0.6, 0.4}, 11L);
//        JavaRDD<LabeledPoint> training = splits[0].cache();
//        JavaRDD<LabeledPoint> test = splits[1];
//
//        // Run training algorithm to build the model.
//        LogisticRegressionModel model = new LogisticRegressionWithLBFGS()
//                .setNumClasses(10)
//                .run(training.rdd());
//
//        // Compute raw scores on the test set.
//        JavaPairRDD<Object, Object> predictionAndLabels = test.mapToPair(p ->
//                new Tuple2<>(model.predict(p.features()), p.label()));
//
//        // Get evaluation metrics.
//        MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
//        double accuracy = metrics.accuracy();
//        System.out.println("Accuracy = " + accuracy);
//
//        // Save and load model
//        model.save(sc, "target/tmp/javaLogisticRegressionWithLBFGSModel");
//        LogisticRegressionModel sameModel = LogisticRegressionModel.load(sc,
//                "target/tmp/javaLogisticRegressionWithLBFGSModel");
//        // $example off$
//
//        sc.stop();
    }
}
