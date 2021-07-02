package com.jrx.cloud.netty.common.codec;

import com.jrx.cloud.common.util.JacksonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@link Invocation} 解码器
 *
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 标记当前读取位置
        in.markReaderIndex();
        // 判断是否能够读取 length 长度
        if (in.readableBytes() <= 4) {
            return;
        }
        // 读取长度
        var length = in.readInt();
        if (length < 0) {
            throw new CorruptedFrameException(String.format("### negative length: %s ###", length));
        }
        // 如果 message 不够可读，则退回到原读取位置
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        // 读取内容
        var content = new byte[length];
        in.readBytes(content);
        // 解析成 Invocation
        var invocation = JacksonUtils.parseObject(content, Invocation.class);
        out.add(invocation);
        log.info("### [Decode] connection {} has decoded a message - {} ###", ctx.channel().id(), invocation);
    }
}
