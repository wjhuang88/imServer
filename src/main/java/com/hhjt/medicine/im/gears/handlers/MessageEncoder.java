package com.hhjt.medicine.im.gears.handlers;

import com.hhjt.medicine.im.gears.message.AbMessageContainer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by GHuang on 15/1/6.
 */
public class MessageEncoder extends MessageToByteEncoder<AbMessageContainer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbMessageContainer in, ByteBuf out) throws Exception {
        out.writeBytes(in.getSource());
    }
}
