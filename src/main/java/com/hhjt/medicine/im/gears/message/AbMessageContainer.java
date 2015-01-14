package com.hhjt.medicine.im.gears.message;

/**
 * Created by GHuang on 14/12/30.
 */
public abstract class AbMessageContainer {

    protected byte[] source;
    private boolean cacheFlag;

    public AbMessageContainer(byte[] source) {
        this.source = source;
        this.cacheFlag = true;
        this.decode();
    }

    public byte[] getSource() {
        if (!cacheFlag) {
            this.encode();
            cacheFlag = true;
        }
        return source;
    }

    protected byte[] encodeHelper(byte[] params, String cmd) {

        int paramLen = params.length;
        int len = paramLen + 16;
        byte[] r = new byte[len];
        try {

            r[0] = '#';
            r[1] = '#';

            System.arraycopy(String.format("%04d", paramLen + 8).getBytes(), 0, r, 2, 4);
            System.arraycopy(cmd.getBytes(), 0, r, 6, 4);
            System.arraycopy(String.format("%04d", paramLen).getBytes(), 0, r, 10, 4);
            System.arraycopy(params, 0, r, 14, paramLen);

            r[len - 2] = 13;
            r[len - 1] = 10;
        } catch (IndexOutOfBoundsException e) {
            r = "##\r\n".getBytes();
        }
        return r;
    }

    protected void flushCache() {
        this.cacheFlag = false;
    }

    protected abstract void decode();
    protected abstract void encode();

    public abstract void doSend();

}
