package com.jrx.cloud.common.config.global;

import com.jrx.cloud.assembly.base.BaseRsp;
import com.jrx.cloud.assembly.error.GlobalError;
import com.jrx.cloud.assembly.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.jrx.cloud.assembly.error.GlobalError.*;

/**
 * 统一异常处理。
 *
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public BaseRsp<Object> handleServiceException(BusinessException e) {
        log.error("### [{}] {} ###", e.getExceptionCode(), e.getExceptionMessage());
        return BaseRsp.error(e);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public BaseRsp<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("### {} ###", e.getMessage(), e);
        return BaseRsp.error(SERVICE_EXCEPTION.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public BaseRsp<Object> handleException(Exception e) {
        log.error("### {} ###", e.getMessage(), e);
        return BaseRsp.error(SERVICE_EXCEPTION);
    }
}
