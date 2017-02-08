package com.qunar.UserBehaviorFilter;

import com.google.common.collect.Lists;
import com.qunar.util.RandomGenerator;

import java.util.Arrays;

/**
 * Created by zhipengwu on 17-1-22.
 */
public class InputArgsParser {
    public static InputArgs parseInputArgs(String[] args){
        int len=args.length;
        InputArgs inputArgs=new InputArgs();
        inputArgs.keywordsList= Lists.newArrayList();
        inputArgs.inputPath=args[0];
        inputArgs.outputPath=args[1];
        inputArgs.reduceNumbers=0; //不需要reduce阶段
        //todo 解决默认分割符的问题(满足多关键字需求)
        inputArgs.keywordsList.addAll(Arrays.asList(Arrays.copyOfRange(args,2,len)));
        inputArgs.separator= RandomGenerator.getSeparator(inputArgs.keywordsList);

    return inputArgs;
    }
}
