package com.jrx.cloud.netty.client.handler;

import com.jrx.cloud.common.util.JacksonUtils;
import com.jrx.cloud.netty.client.config.NettyClient;
import com.jrx.cloud.netty.common.codec.Invocation;
import com.jrx.cloud.netty.common.messgae.heartbeat.HeartbeatRequest;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private final ApplicationContext applicationContext;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 发起重连
        applicationContext.getBean(NettyClient.class).reconnect();
        // 继续触发事件
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("### [exception] an exception has occurred on connection {} ### ", ctx.channel().id(), cause);
        // 断开连接
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        // 空闲时，向服务端发起一次心跳
        if (event instanceof IdleStateEvent) {
            log.info("### [userEventTriggered] Send heartbeat ###");
            ctx.writeAndFlush(new Invocation(HeartbeatRequest.TYPE, JacksonUtils.toJsonString(new HeartbeatRequest())))
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, event);
        }
    }
}
