package com.jrx.cloud.netty.server.handler;

import com.jrx.cloud.netty.server.config.NettyChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final NettyChannelManager channelManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channelManager.add(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        channelManager.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("### [exception] Exception has occurred on connection {} ### ", ctx.channel().id(), cause);
        ctx.channel().close();
    }
}
