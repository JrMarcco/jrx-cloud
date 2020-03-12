package com.jrx.cloud.user.inf.dto;

import com.jrx.cloud.assembly.page.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户查询请求DTO")
@Data
@NoArgsConstructor
public class SysUserQueryDTO extends PageQuery {
    private static final long serialVersionUID = 874860050668109215L;
}
