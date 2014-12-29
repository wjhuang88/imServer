package com.hhjt.medicine.im.gears;

/**
 * Created by GHuang on 14/12/26.
 */
public class UserData {

    private String userId;
    private String userConnectId;

    public UserData() {}

    public UserData(String userId, String userConnectId) {
        this.userId = userId;
        this.userConnectId = userConnectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserConnectId() {
        return userConnectId;
    }

    public void setUserConnectId(String userConnectId) {
        this.userConnectId = userConnectId;
    }
}
