package com.jrx.cloud.websocket.api.constant;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjc
 * @version 1.0  2020/8/21
 */
public class WebsocketConstants {

    public static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new HashMap<>(32);
    public static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final String DEFAULT_GROUP_KEY = "jrx";
}
