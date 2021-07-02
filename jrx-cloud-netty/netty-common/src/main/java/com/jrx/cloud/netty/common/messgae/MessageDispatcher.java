package com.jrx.cloud.netty.common.messgae;

import com.jrx.cloud.common.util.JacksonUtils;
import com.jrx.cloud.netty.common.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) {
        // 获得 type 对应的 MessageHandler 处理器
        var messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
        // 获得 MessageHandler 处理器 的消息类
        var messageClass = messageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        var message = JacksonUtils.parseObject(invocation.getMessage(), messageClass);
        // 执行逻辑
        executor.submit(() -> {
            // noinspection unchecked
            messageHandler.execute(ctx.channel(), message);
        });
    }
}
