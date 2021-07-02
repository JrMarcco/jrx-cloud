package com.jrx.cloud.netty.common.messgae.heartbeat;

import com.jrx.cloud.netty.common.messgae.Message;
import lombok.NoArgsConstructor;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@NoArgsConstructor
public class HeartbeatReq implements Message {

    public static final String TYPE = "HEARTBEAT_REQUEST";

    public static HeartbeatReq instanceOf() {
        return new HeartbeatReq();
    }

    @Override
    public String toString() {
        return "HeartbeatReq: {}";
    }
}
