package com.jrx.cloud.netty.server.handler.heartbeat;

import com.jrx.cloud.netty.common.codec.Invocation;
import com.jrx.cloud.netty.common.messgae.MessageHandler;
import com.jrx.cloud.netty.common.messgae.heartbeat.HeartbeatReq;
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
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatReq> {

    @Override
    public void execute(Channel channel, HeartbeatReq message) {
        log.info("### [Execute] Receive heartbeat request from connection {} ### ###", channel.id());
        channel.writeAndFlush(Invocation.instanceOf(HeartbeatRsp.TYPE, HeartbeatRsp.instanceOf()));
    }

    @Override
    public String getType() {
        return HeartbeatRsp.TYPE;
    }
}
