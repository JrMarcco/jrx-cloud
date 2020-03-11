package com.jrx.cloud.assembly.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Getter
@RequiredArgsConstructor
public enum GlobalError implements IBusinessError {
    SERVICE_EXCEPTION("9999", "服务器异常");

    private final String errorCode;
    private final String errorMessage;
}
