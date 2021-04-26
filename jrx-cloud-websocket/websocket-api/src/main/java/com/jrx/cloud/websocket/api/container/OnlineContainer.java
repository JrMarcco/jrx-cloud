package com.jrx.cloud.websocket.api.container;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongjc
 * @version 1.0  2021/4/26
 */
@Data
@Component
public class OnlineContainer {

    /*
     * <userId,channelId>
     **/
    private Map<String, String> userChannelIdMap = new ConcurrentHashMap<>();

    /*
     * <session,ChannelHandlerContext>
     **/
    private Map<String, ChannelHandlerContext> channelGroup = new ConcurrentHashMap<>();

    public ChannelHandlerContext get(String userId) {
        return channelGroup.getOrDefault(
                userChannelIdMap.getOrDefault(userId, ""), null
        );
    }

    public void put(String userId, ChannelHandlerContext ctx) {
        userChannelIdMap.put(userId, ctx.channel().id().asLongText());
        channelGroup.put(ctx.channel().id().asLongText(), ctx);
    }

    public void remove(String channelId) {
        if (StringUtils.hasLength(channelId) && userChannelIdMap.containsValue(channelId)) {
            for (Map.Entry<String, String> entry : userChannelIdMap.entrySet()) {
                if (channelId.equals(entry.getValue())) {
                    userChannelIdMap.remove(entry.getKey());
                    break;
                }
            }
        }
        channelGroup.remove(channelId);
    }
}
