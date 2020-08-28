package com.intters.enums;

import lombok.Getter;

/**
 * @author Ruison
 * @date 2018/8/11.
 */
@Getter
public enum ResponseEnum {
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 失败
     */
    ERROR(1, "ERROR"),
    ;

    private Integer code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
