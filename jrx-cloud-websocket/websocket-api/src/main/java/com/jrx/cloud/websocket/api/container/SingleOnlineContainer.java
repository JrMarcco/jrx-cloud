package com.jrx.cloud.websocket.api.container;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongjc
 * @version 1.0  2021/4/26
 */
@Slf4j
@Data
@Component
public class SingleOnlineContainer {

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
        disconnectChannel(userChannelIdMap.getOrDefault(userId, null));

        log.info("### Channel {} is connected. ###", ctx.channel().id().asLongText());
        userChannelIdMap.put(userId, ctx.channel().id().asLongText());
        channelGroup.put(ctx.channel().id().asLongText(), ctx);
    }

    public void remove(String channelId) {
        if (StringUtils.hasLength(channelId) && userChannelIdMap.containsValue(channelId)) {
            for (Map.Entry<String, String> entry : userChannelIdMap.entrySet()) {
                if (channelId.equals(entry.getValue())) {
                    disconnectChannel(channelId);
                    userChannelIdMap.remove(entry.getKey());
                    break;
                }
            }
        }
        channelGroup.remove(channelId);
    }

    public void sendTextMsg(String msgContent) {
        channelGroup.values().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(msgContent)));
    }

    public void sendTextMsg(String userId, String msgContent) {
        var channel = get(userId);
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(msgContent));
        }
    }

    private void disconnectChannel(String channelId) {
        if (StringUtils.hasLength(channelId)) {
            ChannelHandlerContext connectedChannel = channelGroup.getOrDefault(channelId, null);
            if (connectedChannel != null) {
                log.info("### Disconnect and close channel {}.. ###", channelId);
                connectedChannel.disconnect().addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
