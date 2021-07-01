package com.jrx.cloud.netty.common.messgae.heartbeat;

import com.jrx.cloud.netty.common.messgae.Message;
import lombok.NoArgsConstructor;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@NoArgsConstructor
public class HeartbeatRsp implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = "HEARTBEAT_RESPONSE";

    @Override
    public String toString() {
        return "HeartbeatRsp: {}";
    }
}
