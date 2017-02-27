package com.qunar.test;

import java.io.Serializable;

/**
 * Created by zhipengwu on 17-2-23.
 */
public class AvgCount implements Serializable {
    public int total_;
    public int num_;
    public float avg(){
        return total_/(float)num_;
    }

    public AvgCount(int total_,int num_){
        total_=total_;
        num_=num_;
    }
}


