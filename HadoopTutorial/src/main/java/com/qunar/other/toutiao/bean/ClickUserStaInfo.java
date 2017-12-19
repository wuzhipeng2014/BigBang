package com.qunar.other.toutiao.bean;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

/**
 * Created by zhipengwu on 17-11-9.
 * 点击用户特征信息统计
 */
public class ClickUserStaInfo {
    public ClickUserStaInfo(){
        clickTimeSet= TreeMultiset.create();
        showTimeSet=TreeMultiset.create();
        userInfoTimeSet=TreeMultiset.create();
    }
    public String md5Keyid;
    public int userInofNum;
    public Multiset<String> clickTimeSet;
    public Multiset<String> showTimeSet;
    public Multiset<String> userInfoTimeSet;
}
