package com.jrx.cloud.netty.common.messgae;

import io.netty.channel.Channel;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
public interface MessageHandler<T extends Message> {
    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param message 消息
     */
    void execute(Channel channel, T message);

    /**
     * @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     */
    String getType();
}
