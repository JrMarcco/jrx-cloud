package com.jrx.cloud.websocket.api.handler;

import com.jrx.cloud.websocket.api.constant.WebsocketConstants;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjc
 * @version 1.0  2020/8/21
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        WebsocketConstants.CHANNEL_GROUP.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        WebsocketConstants.CHANNEL_GROUP_MAP.values().forEach(group -> group.remove(ctx.channel()));
        WebsocketConstants.CHANNEL_GROUP.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            var request = (FullHttpRequest) msg;
            var uri = request.uri();

            log.info("### Receive connect request: {} ###", uri);

            var groupKey = getChannelGroupKey(getUrlParams(uri));
            if (!StringUtils.isEmpty(groupKey)) {
                var gatewayChannelGroup = WebsocketConstants.CHANNEL_GROUP_MAP.get(groupKey);
                if (gatewayChannelGroup == null) {
                    gatewayChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                    WebsocketConstants.CHANNEL_GROUP_MAP.put(groupKey, gatewayChannelGroup);
                }
                gatewayChannelGroup.add(ctx.channel());
            }

            if (uri.contains("?")) {
                var newUri = uri.substring(0, uri.indexOf("?"));
                request.setUri(newUri);
            }

        } else if (msg instanceof TextWebSocketFrame) {
            var json = ((TextWebSocketFrame) msg).text();
            log.info("### Receive text message: {} ###", json);

//            ctx.writeAndFlush(new TextWebSocketFrame(JsonUtils.toJson(new Object())));
        }
        super.channelRead(ctx, msg);
    }

    // ----------------------------------------< Private Method>----------------------------------------
    private static Map<String, Object> getUrlParams(String url) {
        var map = new HashMap<String, Object>();

        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            Arrays.stream(url.split(";")[1].split("&")).forEach(str -> map.put(str.split("=")[0], str.split("=")[1]));
        }
        return map;
    }

    private String getChannelGroupKey(Map<String, Object> params) {
        return (String) params.get(WebsocketConstants.DEFAULT_GROUP_KEY);
    }
}
