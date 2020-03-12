package com.jrx.cloud.user.inf.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@ApiModel("校验用户信息请求DTO")
@Data
@Valid
@NoArgsConstructor
public class ValidateUserReqDTO implements Serializable {
    private static final long serialVersionUID = 6936815518422298204L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "请求参数有误，用户名为空")
    private String username;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "请求参数有误，用户密码为空")
    private String password;
}
