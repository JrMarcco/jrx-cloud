package com.jrx.cloud.websocket.api.handler;

import com.jrx.cloud.websocket.api.container.ChannelContainer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class TextSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelContainer channelContainer;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        channelContainer.remove(ctx.channel().id().asLongText());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof TextWebSocketFrame) {
            handlerWebSocketFrame(ctx, (TextWebSocketFrame) msg);
        }
        super.channelRead(ctx, msg);
    }

    // ----------------------------------------< Private Method>----------------------------------------

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        var uri = request.uri();

        log.info("### Receive connect request: {} ###", uri);
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        var json = msg.text();
        log.info("### Receive text message: {} ###", json);

        ctx.writeAndFlush(new TextWebSocketFrame(""));
    }

    private Map<String, Object> getUrlParams(String url) {
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
}
