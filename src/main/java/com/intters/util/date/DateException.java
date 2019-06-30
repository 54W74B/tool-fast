package com.intters.util.date;

/**
 * @author Ruison
 * @date 2018/7/13.
 */
public class DateException extends RuntimeException {

    private static final long serialVersionUID = 6042492606789570845L;

    public DateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
