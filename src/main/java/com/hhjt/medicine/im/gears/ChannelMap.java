package com.hhjt.medicine.im.gears;

import io.netty.channel.Channel;
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
public class ChannelMap {
    private static ChannelMap ourInstance = new ChannelMap();

    public static ChannelMap getInstance() {
        return ourInstance;
    }

    private Map<Integer, ChannelHandlerContext> channelMap;
    private int idCount;
    private Logger l;

    private ChannelMap() {
        this.channelMap = new HashMap<Integer, ChannelHandlerContext>();
        l = LoggerFactory.getLogger(this.getClass().getSimpleName());
        idCount = 0;
        l.info("Client map initialized.");
    }

    public int addChannel(ChannelHandlerContext c) {
        int count = ++idCount;
        this.channelMap.put(count, c);
        l.info("Client added. id = " + count + " ,socket = " + c.channel().remoteAddress());
        return count;
    }

    public void removeChannel(int id) {
        if (channelMap.containsKey(id)) {
            channelMap.remove(id);
            l.info("Client removed. id = " + id);
        } else {
            l.info("Client not exists. id = " + id);
        }
    }

}
