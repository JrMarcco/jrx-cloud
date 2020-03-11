package com.jrx.cloud.assembly.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hongjc
 * @version 1.0  2020/2/8
 */
@ApiModel(value = "基础分页查询参数")
@Data
@NoArgsConstructor
public class BasePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索关键字，多字段模糊匹配时候用")
    private String keyword;
    @ApiModelProperty(value = "当前页码，默认值1", example = "1")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "当前页面大小，默认值10", example = "10")
    private Integer pageSize = 10;

}
