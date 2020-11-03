package com.jrx.cloud.assembly.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author jrmarcco
 * @version 1.0  2020/11/3
 */
public interface BaseService<Param, Result> {

    /**
     * 分页查询
     *
     * @param param 请求参数DTO
     * @return 分页集合
     */
    default PageInfo<Result> page(BasePageParam<Param> param) {
        return PageHelper.startPage(param).doSelectPageInfo(() -> list(param.getParam()));
    }

    /**
     * 集合查询
     *
     * @param param 查询参数
     * @return 查询响应
     */
    @SuppressWarnings("UnusedReturnValue")
    List<Result> list(Param param);
}
