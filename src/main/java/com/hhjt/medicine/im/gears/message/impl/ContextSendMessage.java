package com.hhjt.medicine.im.gears.message.impl;

import com.hhjt.medicine.im.gears.Utils;
import com.hhjt.medicine.im.gears.Values;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;

/**
 * Created by GHuang on 15/1/5.
 */
public class ContextSendMessage extends AbMessageContainer {

    private String sessionId;
    private String userId;
    private byte[] context;

    public ContextSendMessage(byte[] source) {
        super(source);
    }

    @Override
    protected void decode() {

        int offset1 = Values.PARAM_OFFSET;
        int offset2 = Values.PARAM_OFFSET + 6;
        int len = Utils.charsToInt(source, Values.PARAM_OFFSET  - 4);

        byte[] sessionBytes =  new byte[6], userBytes = new byte[11];

        System.arraycopy(source, offset1, sessionBytes, 0, 6);
        System.arraycopy(source, offset2, userBytes, 0, 11);

        this.userId = new String(sessionBytes);
        this.sessionId = new String(userBytes);

        if (len > 17) {
            this.context = new byte[len - 17];
            System.arraycopy(source, offset2 + 11, this.context, 0, len - 17);
        }
    }

    @Override
    protected void encode() {
        byte[] src = new byte[17 + context.length];
        System.arraycopy((userId + sessionId).getBytes(), 0, src, 0, 17);
        System.arraycopy(context, 0, src, 17, context.length);

        this.source = encodeHelper(src, "JOAS");
    }

    @Override
    public void doSend() {

    }
}
