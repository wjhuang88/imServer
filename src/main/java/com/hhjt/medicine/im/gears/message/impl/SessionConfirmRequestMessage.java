package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class SessionConfirmRequestMessage extends AbMessageContainer {

    private String fromUserId;
    private String toUserId;

    public SessionConfirmRequestMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        int offset1 = Values.PARAM_OFFSET;
        int offset2 = Values.PARAM_OFFSET + 11;

        byte[] fromBytes =  new byte[11], toBytes = new byte[11];

        System.arraycopy(source, offset1, fromBytes, 0, 11);
        System.arraycopy(source, offset2, toBytes, 0, 11);

        fromUserId = new String(fromBytes);
        toUserId = new String(toBytes);

    }

    @Override
    protected void encode() {
        source = this.encodeHelper((fromUserId + toUserId).getBytes(), "JORQ");
    }

    @Override
    public void doSend() {

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
}
