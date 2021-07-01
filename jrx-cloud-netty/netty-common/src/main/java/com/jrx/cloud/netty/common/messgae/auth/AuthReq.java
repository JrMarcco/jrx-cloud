package com.jrx.cloud.netty.common.messgae.auth;

import com.jrx.cloud.common.util.JacksonUtils;
import com.jrx.cloud.netty.common.messgae.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthReq implements Message {
    public static final String TYPE = "AUTH_REQUEST";

    private String accessToken;

    @Override
    public String toString() {
        return "AuthReq: " + JacksonUtils.toJsonString(this);
    }
}
