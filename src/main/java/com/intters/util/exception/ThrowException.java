package com.intters.util.exception;

import com.intters.util.enums.CodeEnum;
import com.intters.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询，插入，更新等操作，判断是否执行成功
 * 提供日志记录，抛出错误
 *
 * @author Ruison
 * @date 2018/7/16.
 */
@Slf4j
public class ThrowException {

    public final static String EXCEPTION_INFORMATION = "【%s】 错误信息描述：{}";

    /**
     * 判断是否为空
     * 查询是否为空
     *
     * @param object 校验对象
     * @param codeEnum 错误信息
     */
    public static void checkNull(Object object, CodeEnum codeEnum) {
        checkNull(object, codeEnum, null);
    }

    /**
     * 判断是否为空
     * 查询是否为空
     *
     * @param object 校验对象
     * @param codeEnum 错误信息
     * @param description 错误信息描述
     */
    public static void checkNull(Object object, CodeEnum codeEnum, String description) {
        if (!ObjectUtil.isNotEmpty(object)) {
            if (StringUtils.isNotEmpty(description)) {
                log.error(String.format(EXCEPTION_INFORMATION, description), codeEnum.getMsg());
            }
            throw new BaseException(codeEnum);
        }
    }

    /**
     * 判断是否小于等于0
     * 判断更新，删除，插入等操作是否成功
     *
     * @param result 更新，删除，插入等操作结果值，int类型
     * @param codeEnum 错误信息
     */
    public static void lessThanZero(int result, CodeEnum codeEnum) {
        lessThanZero(result, codeEnum, null);
    }

    /**
     * 判断是否小于等于0
     * 判断更新，删除，插入等操作是否成功
     *
     * @param result 更新，删除，插入等操作结果值，int类型
     * @param codeEnum 错误信息
     * @param description 错误信息描述
     */
    public static void lessThanZero(Integer result, CodeEnum codeEnum, String description) {
        if (result == null || result <= 0) {
            if (StringUtils.isNotEmpty(description)) {
                log.error(String.format(EXCEPTION_INFORMATION, description), codeEnum.getMsg());
            }
            throw new BaseException(codeEnum);
        }
    }

    /**
     * 判断波尔类型
     * 判断更新，删除，插入等操作是否成功
     *
     * @param result 更新，删除，插入等操作结果值，boolean类型
     * @param codeEnum 错误信息
     */
    public static void checkBoolean(boolean result, CodeEnum codeEnum) {
        checkBoolean(result, codeEnum, null);
    }

    /**
     * 判断波尔类型
     * 判断更新，删除，插入等操作是否成功
     *
     * @param result 更新，删除，插入等操作结果值，boolean类型
     * @param codeEnum 错误信息
     * @param description 错误信息描述
     */
    public static void checkBoolean(boolean result, CodeEnum codeEnum, String description) {
        if (!result) {
            if (StringUtils.isNotEmpty(description)) {
                log.error(String.format(EXCEPTION_INFORMATION, description), codeEnum.getMsg());
            }
            throw new BaseException(codeEnum);
        }
    }

    /**
     * 判断是否大于0
     * 查询是否存在某个值
     *
     * @param result 查询操作等结果值，int类型
     * @param codeEnum 错误信息
     */
    public static void greaterThanZero(int result, CodeEnum codeEnum) {
        greaterThanZero(result, codeEnum, null);
    }

    /**
     * 判断是否大于0
     * 查询是否存在某个值
     *
     * @param result 查询操作等结果值，int类型
     * @param codeEnum 错误信息
     * @param description 错误信息描述
     */
    public static void greaterThanZero(Integer result, CodeEnum codeEnum, String description) {
        if (result == null || result > 0) {
            if (StringUtils.isNotEmpty(description)) {
                log.error(String.format(EXCEPTION_INFORMATION, description), codeEnum.getMsg());
            }
            throw new BaseException(codeEnum);
        }
    }
}
