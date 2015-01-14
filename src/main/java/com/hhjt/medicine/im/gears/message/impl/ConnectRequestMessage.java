package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class ConnectRequestMessage extends AbMessageContainer {

    private String userId;
    private byte userType;

    public ConnectRequestMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        byte[] userIdBytes = new byte[11];
        System.arraycopy(source, Values.PARAM_OFFSET, userIdBytes, 0, 11);
        this.userId = new String(userIdBytes);
        this.userType = source[Values.PARAM_OFFSET + 11];
    }

    @Override
    protected void encode() {
        source = encodeHelper((userId + (char)userType).getBytes(), "CORQ");
    }

    @Override
    public void doSend() {
        System.out.println("creq");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        super.flushCache();
    }

    public byte getUserType() {
        return userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
        super.flushCache();
    }
}
