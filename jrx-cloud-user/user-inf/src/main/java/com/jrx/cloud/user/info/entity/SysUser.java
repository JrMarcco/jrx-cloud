package com.jrx.cloud.user.info.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrx.cloud.assembly.constant.JsonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@ApiModel("系统用户信息")
@Data
@NoArgsConstructor
public class SysUser implements Serializable {
    private static final long serialVersionUID = -7523965974668710420L;

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户性别")
    private Character gender;
    @ApiModelProperty("用户手机号")
    private String phoneNumber;
    @ApiModelProperty("用户头像地址")
    private String avatar;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户角色主键id")
    private Integer roleId;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    private Date createTime;
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    private Date updateTime;
    @ApiModelProperty("是否已删除")
    private Character isDeleted;
}
