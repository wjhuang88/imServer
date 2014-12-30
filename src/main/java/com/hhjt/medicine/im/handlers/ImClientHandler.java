package com.hhjt.medicine.im.handlers;

import com.hhjt.medicine.im.gears.ChannelManager;
import com.hhjt.medicine.im.gears.UserData;
import com.hhjt.medicine.im.gears.UserManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

        ByteBuf in = (ByteBuf) msg;

        StringBuilder msgBuilder = new StringBuilder();
        Character cmd = null;

        String uid;

        if (in.isReadable()) {
            cmd = (char) in.readByte();
        }
        while (in.isReadable()) {
            msgBuilder.append((char) in.readByte());
        }
        if (msgBuilder.length() >= 11) {
            uid = msgBuilder.substring(0, 11);
        } else {
            uid = null;
        }

        String res;
        ChannelHandlerContext toCtx = ctx;
        assert cmd != null;
        switch (cmd) {
            case '0':
                res = "Ping Pong.";
                break;
            case '1':
                um.addUser(new UserData(uid, this.contextId));
                res = "Register.";
                break;
            case '2':
                String to = msgBuilder.substring(11, 22);
                toCtx = cm.getChannel(um.getUser(to).getUserConnectId());
                res = "\nMessage from: " + uid + "\n" + msgBuilder.substring(23);
                break;
            default:
                res = "error";
        }

        ByteBuf out = toCtx.alloc().buffer(res.length());
        out.writeBytes(res.getBytes());
        toCtx.writeAndFlush(out);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        l.error("Connector error, id = " + contextId + " ,detail:" + cause.toString());
        for (StackTraceElement e : cause.getStackTrace()) {
            l.error("~~at " + e.toString());
        }
    }
}
