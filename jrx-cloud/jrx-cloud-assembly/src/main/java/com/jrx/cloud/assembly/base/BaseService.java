package com.jrx.cloud.assembly.base;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import java.util.List;

/**
 * @author jrmarcco
 * @version 1.0  2020/11/3
 */
public interface BaseService<T, R> {

    /**
     * 分页查询
     *
     * @param param 请求参数DTO
     * @return 分页集合
     */
    default PageInfo<R> page(BasePageParam<T> param) {
        return PageMethod.startPage(param).doSelectPageInfo(() -> list(param.getParam()));
    }

    /**
     * 集合查询
     *
     * @param param 查询参数
     * @return 查询响应
     */
    @SuppressWarnings("UnusedReturnValue")
    List<R> list(T param);
}
