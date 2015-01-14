package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Utils;
import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class SessionResponseMessage extends AbMessageContainer {

    private char result;
    private String errInfo;
    private String sessionId;

    public SessionResponseMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        int offset = Values.PARAM_OFFSET;
        int len = Utils.charsToInt(source, offset - 4);

        this.result = ((char) source[offset]);

        errInfo = "";
        sessionId = "";
        if (len > 1) {
            switch (this.result) {
                case '1':
                    this.sessionId = Utils.getStringFromBytes(source, offset + 1, 6); break;
                case '0':
                    this.errInfo = Utils.getStringFromBytes(source, offset + 1, len - 1); break;
            }
        }
    }

    @Override
    protected void encode() {
        source = encodeHelper((String.valueOf(result) + errInfo + sessionId).getBytes(), "COAS");
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

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
        super.flushCache();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        super.flushCache();
    }
}
