package com.jrx.cloud.netty.server.handler;

import com.jrx.cloud.netty.common.codec.InvocationDecoder;
import com.jrx.cloud.netty.common.codec.InvocationEncoder;
import com.jrx.cloud.netty.common.messgae.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Component
@RequiredArgsConstructor
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer HEART_BEAT_TIME_OUT_SECONDS = 3 * 60;

    private final MessageDispatcher messageDispatcher;
    private final NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel channel) {
        // 空闲检测
        channel.pipeline().addLast(new ReadTimeoutHandler(HEART_BEAT_TIME_OUT_SECONDS, TimeUnit.SECONDS));
        // 编码器
        channel.pipeline().addLast(new InvocationEncoder());
        // 解码器
        channel.pipeline().addLast(new InvocationDecoder());
        // 消息分发器
        channel.pipeline().addLast(messageDispatcher);
        // 服务端处理器
        channel.pipeline().addLast(nettyServerHandler);
    }
}
