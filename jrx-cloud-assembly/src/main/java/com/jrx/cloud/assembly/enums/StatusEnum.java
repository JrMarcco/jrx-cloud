package com.jrx.cloud.assembly.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Getter
@RequiredArgsConstructor
public enum StatusEnum {
    ENABLE((byte) 1, "启用"),
    DISABLE((byte) 0, "禁用");

    private final Byte status;
    private final String desc;
}
