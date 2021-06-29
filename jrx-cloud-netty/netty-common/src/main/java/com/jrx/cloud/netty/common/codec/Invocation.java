package com.jrx.cloud.netty.common.codec;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invocation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String type;
    private String message;

    public Invocation setType(String type) {
        this.type = type;
        return this;
    }

    public Invocation setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return String.format("{ type: %s, message: %s}", type, message);
    }
}
