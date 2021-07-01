package com.jrx.cloud.netty.client.handler.heartbeat;

import com.jrx.cloud.netty.common.messgae.MessageHandler;
import com.jrx.cloud.netty.common.messgae.heartbeat.HeartbeatRsp;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@Slf4j
@Component
public class HeartbeatResponseHandler implements MessageHandler<HeartbeatRsp> {

    @Override
    public void execute(Channel channel, HeartbeatRsp message) {
        log.info("### [Execute] Receive heartbeat response from connection {} ###", channel.id());
    }

    @Override
    public String getType() {
        return HeartbeatRsp.TYPE;
    }
}
