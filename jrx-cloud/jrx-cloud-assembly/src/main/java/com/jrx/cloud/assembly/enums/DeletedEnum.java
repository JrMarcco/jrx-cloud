package com.jrx.cloud.assembly.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Getter
@RequiredArgsConstructor
public enum DeletedEnum {
    YES((byte) 1, "已删除"),
    NO((byte) 0, "未删除");

    private final Byte deleted;
    private final String desc;
}
