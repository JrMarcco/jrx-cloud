package com.jrx.cloud.user.info.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@Data
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Character gender;
    private String phoneNumber;
    private String avatar;
    private String email;
    private Integer roleId;
    private Date createTime;
    private Date updateTime;
    private Character isDeleted;
}
