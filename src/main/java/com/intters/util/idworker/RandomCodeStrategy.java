package com.intters.util.idworker;

/**
 * ID接口
 * @author Ruison
 * @date 2018/7/12.
 */
public interface RandomCodeStrategy {

    void init();

    int prefix();

    int next();

    void release();
}
