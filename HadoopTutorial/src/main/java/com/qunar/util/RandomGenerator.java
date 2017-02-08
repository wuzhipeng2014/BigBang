package com.qunar.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * Created by zhipengwu on 17-1-22.
 */
public class RandomGenerator {

    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // todo 修改为抛出异常的方式
    public static String getSeparator(List<String> keysList) {
        String result = null;
        List<String> separatorList = Constant.separatorList;
        int len = keysList.size();
        for (String item : separatorList) {
            for (String key : keysList) {
                if (key.contains(item)) {
                    break;
                }
                if (key.equals(keysList.get(len - 1))) {
                    result = item;
                }
            }
            if (result != null) {
                break;
            }
        }
        Preconditions.checkNotNull(result);
        return result;
    }

    public static void main(String[] args) {
        List<String> sl = Lists.newArrayList("a&b*c", "e$d#f", "k|gkg");
        String separator = getSeparator(sl);
        System.out.println(separator);
    }

}
