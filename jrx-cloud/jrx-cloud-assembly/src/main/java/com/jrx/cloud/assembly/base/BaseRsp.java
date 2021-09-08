package com.jrx.cloud.assembly.base;

import com.jrx.cloud.assembly.enums.BaseResultEnum;
import com.jrx.cloud.assembly.error.IBusinessError;
import com.jrx.cloud.assembly.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Data
@ApiModel(description = "基础响应信息")
public class BaseRsp<T extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("响应码，成功为\"0000\"，其他为具体业务错误码")
    private String code;
    @ApiModelProperty("响应消息，成功为\"success\"，其他为具体错误消息或异常信息")
    private String msg;

    @ApiModelProperty("返回结果")
    private T data;

    public BaseRsp() {
    }

    private BaseRsp(BaseResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    private BaseRsp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private BaseRsp(BaseResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    private BaseRsp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseRsp<String> success() {
        return new BaseRsp<>(BaseResultEnum.SUCCESS);
    }

    public static <T extends Serializable> BaseRsp<T> success(T data) {
        return new BaseRsp<>(BaseResultEnum.SUCCESS, data);
    }

    public static <T extends Serializable> BaseRsp<T> error(String code, String msg) {
        return new BaseRsp<>(code, msg);
    }

    public static <T extends Serializable> BaseRsp<T> error(IBusinessError err) {
        return new BaseRsp<>(err.getErrorCode(), err.getErrorMessage());
    }

    public static <T extends Serializable> BaseRsp<T> error(BusinessException exception) {
        return new BaseRsp<>(exception.getExceptionCode(), exception.getExceptionMessage());
    }

    protected Boolean isSuccess() {
        return StringUtils.hasLength(code) && BaseResultEnum.SUCCESS.getCode().equals(code);
    }
}
