package com.jrx.cloud.websocket.api.handler;

import com.jrx.cloud.websocket.api.container.ChannelContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
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

        if (!request.decoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 权限校验
        var requestParams = getUriParams(uri);

        sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        var json = msg.text();
        log.info("### Receive text message: {} ###", json);

        ctx.writeAndFlush(new TextWebSocketFrame(""));
    }

    private Map<String, Object> getUriParams(String uri) {
        var map = new HashMap<String, Object>();

        uri = uri.replace("?", ";");
        if (!uri.contains(";")) {
            return map;
        }
        if (uri.split(";").length > 0) {
            Arrays.stream(uri.split(";")[1].split("&")).forEach(str -> map.put(str.split("=")[0], str.split("=")[1]));
        }
        return map;
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if (!HttpResponseStatus.OK.equals(response.status())) {
            var byteBuf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(byteBuf);
            byteBuf.release();
        }

        var channelFuture = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) || !HttpResponseStatus.OK.equals(response.status())) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
