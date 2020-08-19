package com.intters.file.ftp;

/**
 * @author Ruison
 * on 2019/8/31 - 15:32
 */
public class IORuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8490149159895201756L;


    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
