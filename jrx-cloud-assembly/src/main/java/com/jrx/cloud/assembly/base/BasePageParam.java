package com.jrx.cloud.assembly.base;

import com.github.pagehelper.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author x
 * @version 1.0  2020/11/3
 */
@Data
@Accessors(chain = true)
public class BasePageParam<T> implements IPage {

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页数", example = "20")
    private Integer pageSize = 20;

    @ApiModelProperty(value = "页数", example = "id desc")
    private String orderBy;

    @ApiModelProperty(value = "参数")
    private T param;

    public BasePageParam<T> setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }
}
