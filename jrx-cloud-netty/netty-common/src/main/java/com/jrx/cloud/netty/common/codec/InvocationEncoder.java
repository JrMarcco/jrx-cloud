package com.jrx.cloud.netty.common.codec;

import com.jrx.cloud.common.util.JacksonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link Invocation} 编码器
 *
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) {
        var content = JacksonUtils.toJsonBytes(invocation);
        out.writeInt(content.length);
        out.writeBytes(content);
        log.info("### [Encode] connection {} has encoded a message - {} ###", ctx.channel().id(), invocation);
    }
}
