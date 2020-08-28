package com.intters.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.intters.enums.CodeEnum;
import lombok.Getter;

/**
 * 基础自定义异常
 *
 * @author Ruison
 * @date 2018/7/16.
 */
@Getter
public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(CodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }
}
