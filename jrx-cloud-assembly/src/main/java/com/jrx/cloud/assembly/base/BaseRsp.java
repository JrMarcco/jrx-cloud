package com.jrx.cloud.assembly.base;

import com.jrx.cloud.assembly.constant.BaseConstants;
import com.jrx.cloud.assembly.error.IBusinessError;
import com.jrx.cloud.assembly.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Data
@ApiModel(value = "基础响应信息")
public class BaseRsp<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "响应码，成功为\"0000\"，其他为具体业务错误码")
    private String code;
    @ApiModelProperty(value = "响应消息，成功为\"success\"，其他为具体错误消息或异常信息")
    private String msg;

    @ApiModelProperty(value = "返回结果")
    private T data;

    @ApiModelProperty(value = "总数，分页查询时生效")
    private Integer total;

    public BaseRsp() {
    }

    public BaseRsp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseRsp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseRsp<T> of(BusinessException exception) {
        return new BaseRsp<>(exception.getExceptionCode(), exception.getExceptionMessage());
    }

    public static BaseRsp<Void> success() {
        return new BaseRsp<>(BaseConstants.RESULT_CODE_SUCCESS, BaseConstants.RESULT_MSG_SUCCESS);
    }

    public static <T> BaseRsp<T> success(T data) {
        return new BaseRsp<>(
                BaseConstants.RESULT_CODE_SUCCESS,
                BaseConstants.RESULT_MSG_SUCCESS,
                data
        );
    }

    public static <T> BaseRsp<T> error(String code, String msg) {
        return new BaseRsp<>(code, msg);
    }

    public static <T> BaseRsp<T> error(IBusinessError err) {
        return new BaseRsp<>(err.getErrorCode(), err.getErrorMessage());
    }
}
