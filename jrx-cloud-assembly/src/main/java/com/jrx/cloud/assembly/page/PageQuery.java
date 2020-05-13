package com.jrx.cloud.assembly.page;

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
 * @version 1.0  2020/2/8
 */
@ApiModel(value = "分页查询参数")
@Data
@NoArgsConstructor
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "搜索关键字，多字段模糊匹配时候使用")
    protected String keyword;
    @ApiModelProperty(value = "当前页码，默认值1", example = "1")
    protected Integer pageNum = 1;
    @ApiModelProperty(value = "当前页面大小，默认值10", example = "10")
    protected Integer pageSize = 10;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    protected Date startTime;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = JsonConstants.DATE_TIME_PATTERN, timezone = JsonConstants.TIME_ZONE_GMT8)
    protected Date endTime;
}
