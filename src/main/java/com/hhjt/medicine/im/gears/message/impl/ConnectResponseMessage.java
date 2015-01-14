package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Utils;
import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class ConnectResponseMessage extends AbMessageContainer {

    private char result;
    private String errInfo;

    public ConnectResponseMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        int offset = Values.PARAM_OFFSET;
        int len = Utils.charsToInt(source, offset - 4);
        this.result = ((char) source[offset]);
        if (len > 1) {
            this.errInfo = Utils.getStringFromBytes(source, offset + 1, len - 1);
        } else {
            this.errInfo = "";
        }
    }

    @Override
    protected void encode() {

        source = encodeHelper((String.valueOf(result) + errInfo).getBytes(), "COAS");
    }

    @Override
    public void doSend() {
        System.out.println("cres");
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
}
