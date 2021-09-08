package com.jrx.cloud.assembly.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@ApiModel(description = "分页数据对象")
@Data
@NoArgsConstructor
public class PageData<T extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页面数据")
    private List<T> list;
    @ApiModelProperty(value = "总数", example = "0")
    private Integer total = 0;

    private PageData(List<T> list, Integer total) {
        this.list = list;
        this.total = total;
    }

    public static <T extends Serializable> PageData<T> empty() {
        return new PageData<>(Collections.emptyList(), 0);
    }

    public static <T extends Serializable> PageData<T> of(List<T> list, Integer total) {
        return new PageData<>(list, total);
    }
}
