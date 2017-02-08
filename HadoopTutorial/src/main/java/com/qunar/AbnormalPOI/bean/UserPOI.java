package com.qunar.AbnormalPOI.bean;

/**
 * Created by zhipengwu on 16-10-31.
 */
public class UserPOI {
    public UserPOI() {
    }

    public UserPOI(String gid, String actionTime, String userCityName, String businessType) {
        this.gid = gid;
        this.actionTime = actionTime;
        this.userCityName = userCityName;
        this.businessType = businessType;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getUserCityName() {
        return userCityName;
    }

    public void setUserCityName(String userCityName) {
        this.userCityName = userCityName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private String gid;
    private String actionTime;
    private String userCityName;
    private String businessType;

    public static void main(String[] args) {
    }
}
