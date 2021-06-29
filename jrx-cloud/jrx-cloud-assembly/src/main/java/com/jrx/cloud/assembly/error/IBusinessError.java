package com.jrx.cloud.assembly.error;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
public interface IBusinessError {
    /**
     * 获取业务错误编码。
     */
    String getErrorCode();

    /**
     * 获取业务错误信息。
     */
    String getErrorMessage();
}
