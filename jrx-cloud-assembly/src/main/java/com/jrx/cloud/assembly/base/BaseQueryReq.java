package com.jrx.cloud.assembly.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hongjc
 * @version 1.0  2020/3/11
 */
@ApiModel(value = "基础查询对象")
@Data
@NoArgsConstructor
public class BaseQueryReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @NotNull(message = "请求参数有误，主键id为空")
    private Integer id;
}
