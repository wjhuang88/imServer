package com.hhjt.medicine.im.handlers;

import com.hhjt.medicine.im.gears.ChannelManager;
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

    private ChannelManager cm = ChannelManager.getInstance();
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
        ChannelHandlerContext toCtx = cm.getChannel("single://172.16.40.13:54857/user");
        toCtx.write(msg);
        toCtx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        l.error("Connector error, id = " + contextId + " ,detail:" + cause.toString());
        for (StackTraceElement e : cause.getStackTrace()) {
            l.debug("~~at " + e.toString());
        }
    }
}
