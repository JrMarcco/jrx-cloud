package com.jrx.cloud.user.api.service.impl;

import com.jrx.cloud.user.api.mapper.SysUserMapper;
import com.jrx.cloud.user.api.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserService implements ISysUserService {

    private final SysUserMapper sysUserMapper;
}
