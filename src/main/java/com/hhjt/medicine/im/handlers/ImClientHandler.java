package com.hhjt.medicine.im.handlers;

import com.hhjt.medicine.im.gears.ChannelManager;
import com.hhjt.medicine.im.gears.UserData;
import com.hhjt.medicine.im.gears.UserManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2014/12/24.
 *
 * @author Huang Weijie.
 */
public class ImClientHandler extends ChannelInboundHandlerAdapter {

    private static ChannelManager cm = ChannelManager.getInstance();
    private static UserManager um = UserManager.getInstance();

    private String contextId;

    private static Logger l = LoggerFactory.getLogger(ImClientHandler.class);

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
        ByteBuf in = (ByteBuf) msg;
        ByteBuf content = ctx.alloc().buffer(4);
        content.writeBytes(in);
        in.release();
        l.info("get:");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        l.error("Connector error, id = " + contextId + " ,detail:" + cause.toString());
        for (StackTraceElement e : cause.getStackTrace()) {
            l.debug("~~at " + e.toString());
        }
    }
}
