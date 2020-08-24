package com.intters.idworker;

import com.intters.exception.BaseException;

/**
 *
 * @author Ruison
 * @date 2018/7/12.
 */
public class InvalidSystemClock extends BaseException {
    public InvalidSystemClock(String message) {
        super(message);
    }
}
