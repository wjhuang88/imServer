package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class SessionQuitRequestMessage extends AbMessageContainer {

    private String sessionId;
    private String userId;

    public SessionQuitRequestMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        int offset1 = Values.PARAM_OFFSET;
        int offset2 = Values.PARAM_OFFSET + 6;

        byte[] sessionBytes =  new byte[6], userBytes = new byte[11];

        System.arraycopy(source, offset1, sessionBytes, 0, 6);
        System.arraycopy(source, offset2, userBytes, 0, 11);

        this.sessionId = new String(sessionBytes);
        this.userId = new String(userBytes);
    }

    @Override
    protected void encode() {
        source = this.encodeHelper((sessionId + userId).getBytes(), "EXRQ");
    }

    @Override
    public void doSend() {

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        super.flushCache();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        super.flushCache();
    }
}
