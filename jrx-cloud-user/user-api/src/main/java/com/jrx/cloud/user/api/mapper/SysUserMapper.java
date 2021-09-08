package com.jrx.cloud.user.api.mapper;

import com.jrx.cloud.user.info.entity.SysUser;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysUser sysUser);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser sysUser);
}
