package com.jrx.cloud.assembly.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrx.cloud.assembly.constant.JsonConstants;
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
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    protected Date createTime;
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    protected Date updateTime;
    @ApiModelProperty("创建用户id")
    protected String createUser;
    @ApiModelProperty("更新用户id")
    protected String updateUser;
}
