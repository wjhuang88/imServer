package com.hhjt.medicine.im.handlers;

import com.hhjt.medicine.im.gears.ChannelMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Created on 2014/12/24.
 *
 * @author Huang Weijie.
 */
public class ImClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelMap cm = ChannelMap.getInstance();
    private int contextId;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.contextId = cm.addChannel(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cm.removeChannel(contextId);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ctx.write(msg);
        ctx.flush();
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        cm.removeChannel(contextId);
        ctx.close();
    }
}
