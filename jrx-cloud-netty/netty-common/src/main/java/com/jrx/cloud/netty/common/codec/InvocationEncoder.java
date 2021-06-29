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
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) throws Exception {
        var content = JacksonUtils.toJSonBytes(invocation);
        // 写入 length
        out.writeInt(content.length);
        // 写入内容
        out.writeBytes(content);
        log.info("### [encode] connection {} has encoded a message - {} ###", ctx.channel().id(), invocation);
    }
}
