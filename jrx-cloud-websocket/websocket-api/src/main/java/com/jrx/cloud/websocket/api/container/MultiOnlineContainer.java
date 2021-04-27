package com.jrx.cloud.websocket.api.container;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author x
 * @version 1.0  2021/4/27
 */
@Data
@Component
public class MultiOnlineContainer {

    /*
     * <userId,channelGroup>
     **/
    private Map<String, DefaultChannelGroup> userChannelGroupMap = new ConcurrentHashMap<>();

    public DefaultChannelGroup get(String userId) {
        return userChannelGroupMap.getOrDefault(userId, null);
    }

    public void put(String userId, ChannelHandlerContext ctx) {
        var channelGroup = userChannelGroupMap.getOrDefault(userId, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        channelGroup.add(ctx.channel());
        userChannelGroupMap.put(userId, channelGroup);
    }

    public void remove(Channel channel) {
        userChannelGroupMap.values().forEach(channels -> channels.remove(channel));
    }

    public void sendTextMsg(String msgContent) {
        userChannelGroupMap.values().forEach(channelGroup -> channelGroup.writeAndFlush(new TextWebSocketFrame(msgContent)));
    }

    public void sendTextMsg(String userId, String msgContent) {
        var channelGroup = get(userId);
        if (channelGroup != null) {
            channelGroup.writeAndFlush(new TextWebSocketFrame(msgContent));
        }
    }
}
