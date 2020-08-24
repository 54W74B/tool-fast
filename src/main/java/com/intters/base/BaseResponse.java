package com.intters.base;

import com.intters.util.enums.CodeEnum;
import com.intters.util.enums.ResponseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息实体类
 *
 * @author Ruison
 * @date 2018/8/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "响应信息")
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 7135745943966596269L;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int code;
    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息")
    private String message;
    /**
     * 响应内容
     */
    @ApiModelProperty(value = "响应内容")
    private T data;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> BaseResponse<T> ok(CodeEnum codeEnum, T data) {
        return new BaseResponse<T>(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<T>(ResponseEnum.SUCCESS.getCode(), message);
    }

    public static <T> BaseResponse<T> ok(String message, T date) {
        return new BaseResponse<T>(ResponseEnum.SUCCESS.getCode(), message, date);
    }

    public static <T> BaseResponse<T> ok(T date) {
        return new BaseResponse<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), date);
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return new BaseResponse<T>(code, message);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<T>(ResponseEnum.ERROR.getCode(), message);
    }

    public static <T> BaseResponse<T> error(CodeEnum codeEnum) {
        return new BaseResponse<T>(codeEnum.getCode(), codeEnum.getMsg());
    }
}
