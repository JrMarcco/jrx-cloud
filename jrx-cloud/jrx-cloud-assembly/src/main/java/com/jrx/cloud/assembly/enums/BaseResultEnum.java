package com.jrx.cloud.assembly.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
@Getter
@RequiredArgsConstructor
public enum BaseResultEnum {

    SUCCESS("0000", "success");

    private final String code;
    private final String msg;
}
