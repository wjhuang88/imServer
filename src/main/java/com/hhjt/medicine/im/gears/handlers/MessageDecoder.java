package com.hhjt.medicine.im.gears.handlers;

import com.hhjt.medicine.im.gears.Utils;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;
import com.hhjt.medicine.im.gears.message.MessageContainerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GHuang on 15/1/6.
 */
public class MessageDecoder extends ByteToMessageDecoder {

    private List<Byte> cList = new ArrayList<Byte>(20);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> list) throws Exception {

        for (;;) {
            byte[] rstArr = null, digits = null;
            int lenOffset = 0, segmentLen = -1, index = 0, packFlag = 0;
            boolean leftFlag = true;
            while ((!(cList.isEmpty()) && leftFlag) || msg.isReadable()) {
                byte c;
                if ((!(cList.isEmpty()) && leftFlag)) {
                    c = cList.remove(0);
                } else {
                    c = msg.readByte();
                }
                if (segmentLen != -1 && index >= segmentLen) {
                    cList.add(c);
                    leftFlag = false;
                    continue;
                }
                if (c == '#') {
                    ++packFlag;
                    continue;
                } else if (packFlag < 2) {
                    packFlag = 0;
                    continue;
                }
                if (packFlag == 2) {
                    if (lenOffset < 4) {
                        if (digits == null) {
                            digits = new byte[4];
                        }
                        digits[lenOffset++] = c;
                        continue;
                    } else if (lenOffset++ == 4) {
                        segmentLen = Utils.charsToInt(digits, 0);
                    }
                    if (rstArr == null) {
                        rstArr = new byte[segmentLen + 6];
                    }

                    int arrayIndex = index++ + 6;
                    rstArr[arrayIndex] = c;
                }
            }

            if (rstArr == null || rstArr.length < 6) {
                rstArr = "##0000ERRO\r\n".getBytes();
            } else {
                rstArr[0] = '#';
                rstArr[1] = '#';
                System.arraycopy(String.format("%04d", segmentLen).getBytes(), 0, rstArr, 2, 4);
            }
            //System.out.println(new String(rstArr));
            AbMessageContainer rst = MessageContainerFactory.getMessageContainer(rstArr);
            if (rst != null) {
                list.add(rst);
            }
            if (cList.isEmpty())
                break;
        }
    }
}
