package com.jrx.cloud.assembly.exception;

import com.jrx.cloud.assembly.error.GlobalError;
import com.jrx.cloud.assembly.error.IBusinessError;

import java.io.Serial;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
public class BusinessException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final IBusinessError err;

    public BusinessException(IBusinessError err) {
        this.err = err;
    }

    public BusinessException(String msg) {
        super(msg);
        this.err = GlobalError.SERVICE_EXCEPTION;
    }

    public String getExceptionCode() {
        return err == null ? GlobalError.SERVICE_EXCEPTION.getErrorCode() : err.getErrorCode();
    }

    public String getExceptionMessage() {
        return err == null ? super.getMessage() : err.getErrorMessage();
    }
}
