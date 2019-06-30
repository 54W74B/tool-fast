package com.intters.util.idworker;

/**
 *
 * @author Ruison
 * @date 2018/7/12.
 */
public interface WorkerIdStrategy {

    void initialize();

    long availableWorkerId();

    void release();
}
