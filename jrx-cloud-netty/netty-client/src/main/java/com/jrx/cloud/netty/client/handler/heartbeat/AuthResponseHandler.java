package com.jrx.cloud.netty.client.handler.heartbeat;

import com.jrx.cloud.netty.common.messgae.MessageHandler;
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
public class AuthResponseHandler implements MessageHandler<AuthRsp> {

    @Override
    public void execute(Channel channel, AuthRsp message) {
        log.info("### [Execute] Receive {} from connection {} ### ###", message.toString(), channel.id());
    }

    @Override
    public String getType() {
        return AuthRsp.TYPE;
    }
}
