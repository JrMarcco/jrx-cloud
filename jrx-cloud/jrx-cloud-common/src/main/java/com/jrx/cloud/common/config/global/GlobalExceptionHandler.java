package com.jrx.cloud.common.config.global;

import com.jrx.cloud.assembly.base.BaseRsp;
import com.jrx.cloud.assembly.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.jrx.cloud.assembly.error.GlobalError.*;

/**
 * 统一异常处理。
 *
 * @author jrmarcco
 * @version 1.0  2020/3/2
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public BaseRsp<String> handleServiceException(BusinessException e) {
        log.error("### [{}] {} ###", e.getExceptionCode(), e.getExceptionMessage());
        return BaseRsp.error(e.getExceptionCode(), e.getExceptionMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseRsp<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("### {} ###", e.getMessage(), e);

        var result = e.getBindingResult();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                var fieldError = (FieldError) objectError;
                log.warn("### Field check failure: object={}, field={}, errorMessage={}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            });

            return BaseRsp.error(
                    PARAM_EXCEPTION.getErrorCode(),
                    result.getFieldError() == null ? PARAM_EXCEPTION.getErrorMessage() : result.getFieldError().getDefaultMessage()
            );
        }

        return BaseRsp.error(PARAM_EXCEPTION);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public BaseRsp<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("### {} ###", e.getMessage(), e);
        return BaseRsp.error(SERVICE_EXCEPTION.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public BaseRsp<String> handleException(Exception e) {
        log.error("### {} ###", e.getMessage(), e);
        return BaseRsp.error(SERVICE_EXCEPTION);
    }
}
