package com.jrx.cloud.netty.client.handler;

import com.jrx.cloud.netty.common.codec.InvocationDecoder;
import com.jrx.cloud.netty.common.codec.InvocationEncoder;
import com.jrx.cloud.netty.common.messgae.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer HEART_BEAT_TIME_OUT_SECONDS = 3 * 60;

    private final MessageDispatcher messageDispatcher;

    private final NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // 空闲检测
                .addLast(new IdleStateHandler(HEART_BEAT_TIME_OUT_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(HEART_BEAT_TIME_OUT_SECONDS))
                // 编码器
                .addLast(new InvocationEncoder())
                // 解码器
                .addLast(new InvocationDecoder())
                // 消息分发器
                .addLast(messageDispatcher)
                // 客户端处理器
                .addLast(nettyClientHandler)
        ;
    }
}
