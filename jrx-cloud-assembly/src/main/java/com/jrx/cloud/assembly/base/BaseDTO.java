package com.jrx.cloud.assembly.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hongjc
 * @version 1.0  2020/3/11
 */
@ApiModel(value = "通用基础视图对象信息")
@Data
@NoArgsConstructor
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    protected Long id;

    @ApiModelProperty("创建时间")
    protected Date createTime;
    @ApiModelProperty("更新时间")
    protected Date updateTime;
    @ApiModelProperty("创建用户id")
    protected String createUser;
    @ApiModelProperty("更新用户id")
    protected String updateUser;
}
