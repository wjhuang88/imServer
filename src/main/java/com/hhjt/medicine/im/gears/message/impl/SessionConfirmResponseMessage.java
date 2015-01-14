package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Utils;
import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class SessionConfirmResponseMessage extends AbMessageContainer {

    private char result;
    private String sessionId;
    private String fromUserId;
    private String toUserId;
    private String errInfo;

    public SessionConfirmResponseMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {


        int offset = Values.PARAM_OFFSET;
        int len = Utils.charsToInt(source, offset - 4);
        this.result = ((char) source[offset]);
        byte[] sessionBytes = new byte[6],
                fromBytes = new byte[11],
                toBytes = new byte[11];
        System.arraycopy(source, offset + 1, sessionBytes, 0, 6);
        System.arraycopy(source, offset + 7, fromBytes, 0, 11);
        System.arraycopy(source, offset + 18, toBytes, 0, 11);

        this.sessionId = new String(sessionBytes);
        this.fromUserId = new String(fromBytes);
        this.toUserId = new String(toBytes);

        if (len > 29) {
            this.errInfo = Utils.getStringFromBytes(source, offset + 29, len - 29);
        } else {
            this.errInfo = "";
        }
    }

    @Override
    protected void encode() {
        source = encodeHelper((String.valueOf(result) + sessionId + fromUserId + toUserId + errInfo).getBytes(), "JOAS");
    }

    @Override
    public void doSend() {

    }

    public char getResult() {
        return result;
    }

    public void setResult(char result) {
        this.result = result;
        super.flushCache();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        super.flushCache();
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        super.flushCache();
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
        super.flushCache();
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
        super.flushCache();
    }
}
