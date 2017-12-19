package com.qunar.work.toutiao;

import com.qunar.work.bean.LRPredictResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.common.Strings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import static com.qunar.work.UserActivity.gson;

/**
 * Created by zhipengwu on 17-11-16.
 */
public class CalLRPredictResult {
    public static void main(String[] args) {
        String inputFile="/home/zhipengwu/secureCRT/lrModel_result.json";

        String labelFile=inputFile+".label";
        String predictProbabilityFile=inputFile+".pro";
       DecimalFormat DECIMALFORMATER = new DecimalFormat("######0.000");


        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(inputFile));
            FileWriter fwLabel=new FileWriter(labelFile);
            FileWriter fwProbability=new FileWriter(predictProbabilityFile);
            while (lineIterator.hasNext()){
                String s = lineIterator.nextLine();
                if (!Strings.isNullOrEmpty(s)){
                    LRPredictResult lrPredictResult = gson.fromJson(s, LRPredictResult.class);
                    if (lrPredictResult==null){
                        return;
                    }

                    LRPredictResult.Probability probability= lrPredictResult.probability;
                    List<Double> values = probability.values;
                    if (values.size()==2){
                        Double posProbability = values.get(1);
                        double label = lrPredictResult.label;
                        double prediction = lrPredictResult.prediction;

//                        if (posProbability>0.243&&prediction<1){
//                            System.out.println(lrPredictResult.keyid);
//                        }
                        fwLabel.append(String.valueOf((int)label)+"\n");
                        fwProbability.append(DECIMALFORMATER.format(posProbability)+"\n");
                    }


                }
            }

            lineIterator.close();
            fwLabel.close();
            fwProbability.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
}
