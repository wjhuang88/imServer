package com.hhjt.medicine.im.gears.handlers;

import com.hhjt.medicine.im.gears.ChannelManager;
import com.hhjt.medicine.im.gears.UserManager;
import com.hhjt.medicine.im.gears.message.AbMessageContainer;
import com.hhjt.medicine.im.gears.message.impl.ConnectRequestMessage;
import com.hhjt.medicine.im.gears.message.impl.ConnectResponseMessage;
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

    private Logger l = LoggerFactory.getLogger(ImClientHandler.class);

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
        ((AbMessageContainer) msg).doSend();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        l.error("Connector error, id = " + contextId + " ,detail:" + cause.toString());
        for (StackTraceElement e : cause.getStackTrace()) {
            l.error("~~at " + e.toString());
        }
    }
}
