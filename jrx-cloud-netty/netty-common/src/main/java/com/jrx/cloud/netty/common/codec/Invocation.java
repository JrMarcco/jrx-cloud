package com.jrx.cloud.netty.common.codec;

import com.jrx.cloud.common.util.JacksonUtils;
import com.jrx.cloud.netty.common.messgae.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通讯消息体
 *
 * @author x
 * @version 1.0  2021/6/29
 */
@Data
@NoArgsConstructor
public class Invocation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String type;
    private String message;

    private Invocation(String type, Message message) {
        this.type = type;
        this.message = JacksonUtils.toJsonString(message);
    }

    public static Invocation instanceOf(String type, Message message) {
        return new Invocation(type, message);
    }

    public String toString() {
        return String.format("{ type: %s, message: %s}", type, message);
    }
}
