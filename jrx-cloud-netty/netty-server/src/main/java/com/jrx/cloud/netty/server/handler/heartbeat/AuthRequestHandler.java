package com.jrx.cloud.netty.server.handler.heartbeat;

import com.jrx.cloud.netty.common.codec.Invocation;
import com.jrx.cloud.netty.common.messgae.MessageHandler;
import com.jrx.cloud.netty.common.messgae.auth.AuthReq;
import com.jrx.cloud.netty.common.messgae.auth.AuthRsp;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@Slf4j
@Component
public class AuthRequestHandler implements MessageHandler<AuthReq> {
    @Override
    public void execute(Channel channel, AuthReq message) {
        log.info("### [Execute] Receive {} from connection {} ### ###", message.toString(), channel.id());

        channel.writeAndFlush(Invocation.instanceOf(AuthRsp.TYPE, new AuthRsp()));
    }

    @Override
    public String getType() {
        return AuthReq.TYPE;
    }
}
