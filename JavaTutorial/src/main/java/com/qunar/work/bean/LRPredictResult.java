package com.qunar.work.bean;

import java.util.List;

/**
 * Created by zhipengwu on 17-11-16.
 */
public class LRPredictResult {
    public double label;
    public Probability probability;
    public double prediction;
    public String keyid;


    public class Probability{
       public int type;
       public List<Double> values;
    }
}
