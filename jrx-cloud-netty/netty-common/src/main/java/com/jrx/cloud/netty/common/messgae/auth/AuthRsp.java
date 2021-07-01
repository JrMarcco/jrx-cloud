package com.jrx.cloud.netty.common.messgae.auth;

import com.jrx.cloud.assembly.constant.BaseConstants;
import com.jrx.cloud.common.util.JacksonUtils;
import com.jrx.cloud.netty.common.messgae.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author x
 * @version 1.0  2021/7/1
 */
@Data
@Builder
@AllArgsConstructor
public class AuthRsp implements Message {

    public static final String TYPE = "AUTH_RESPONSE";

    private String code;
    private String msg;

    public AuthRsp() {
        this.code = BaseConstants.RESULT_CODE_SUCCESS;
        this.msg = BaseConstants.RESULT_MSG_SUCCESS;
    }

    @Override
    public String toString() {
        return "AuthRsp: " + JacksonUtils.toJsonString(this);
    }
}
