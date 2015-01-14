package com.hhjt.medicine.im.gears;

/**
 * Created by GHuang on 15/1/9.
 */
public abstract class Utils {

    public static int charsToInt(byte[] src, int offset) {

        int result = 0, digit;
        for (int i = 0; i < 4; i++) {
            result = (result << 3) + (result << 1);
            switch (src[offset + i]) {
                case '0':digit = 0; break;
                case '1':digit = 1; break;
                case '2':digit = 2; break;
                case '3':digit = 3; break;
                case '4':digit = 4; break;
                case '5':digit = 5; break;
                case '6':digit = 6; break;
                case '7':digit = 7; break;
                case '8':digit = 8; break;
                case '9':digit = 9; break;
                default:digit = 0;
            }
            result += digit;
        }
        return result;
    }

    public static String getStringFromBytes(byte[] src, int offset, int len) {
        byte[] errBytes = new byte[len];
        int n = (offset + len <= src.length)?(len):(src.length - offset);
        System.arraycopy(src, offset, errBytes, 0, n);
        return new String(errBytes);
    }

}
