package com.hhjt.medicine.im.gears;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2014/12/24.
 *
 * @author Huang Weijie.
 */
public class ChannelManager {
    private static ChannelManager ourInstance = new ChannelManager();

    public static ChannelManager getInstance() {
        return ourInstance;
    }

    private Map<String, ChannelHandlerContext> channelMap;
    private Logger l = LoggerFactory.getLogger(ChannelManager.class);

    private ChannelManager() {
        this.channelMap = new HashMap<String, ChannelHandlerContext>();
        l.info("Client map initialized.");
    }

    public String addChannel(ChannelHandlerContext c) {
        String id = "single:/" + c.channel().remoteAddress().toString() + "/user";
        this.channelMap.put(id, c);
        l.info("Client connected, id = " + id);
        return id;
    }

    public ChannelHandlerContext removeChannel(String id) {
        if (channelMap.containsKey(id)) {
            ChannelHandlerContext removed = channelMap.remove(id);
            l.info("Client disconnected, id = " + id);
            return removed;
        } else {
            l.info("Client not exists, id = " + id);
            return null;
        }
    }

    public ChannelHandlerContext getChannel(String id) {
        if (channelMap.containsKey(id)) {
            return channelMap.get(id);
        } else {
            l.info("Client not exists, id = " + id);
            return null;
        }
    }

}
