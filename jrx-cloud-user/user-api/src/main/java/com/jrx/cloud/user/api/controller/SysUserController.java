package com.jrx.cloud.user.api.controller;

import com.jrx.cloud.assembly.base.BaseRsp;
import com.jrx.cloud.assembly.exception.BusinessException;
import com.jrx.cloud.user.api.service.ISysUserService;
import com.jrx.cloud.user.inf.dto.SysUserQueryDTO;
import com.jrx.cloud.user.inf.dto.ValidateUserReqDTO;
import com.jrx.cloud.user.info.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@Api(value = "系统用户接口", tags = "系统用户")
@Slf4j
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService sysUserService;

    @ApiOperation(value = "校验用户信息")
    public BaseRsp<Void> validateUser(@RequestBody ValidateUserReqDTO req) {
        return BaseRsp.success();
    }

    @ApiOperation("分页查询用户信息")
    @PostMapping("/pageQuery")
    public BaseRsp<List<SysUser>> pageQuery(@RequestBody SysUserQueryDTO dto) {
        return BaseRsp.success(Collections.<SysUser>emptyList());
    }
}
