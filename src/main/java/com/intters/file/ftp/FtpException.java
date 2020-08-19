package com.intters.file.ftp;

/**
 * @author Ruison
 * on 2019/8/31 - 14:33
 */
public class FtpException extends RuntimeException {
    private static final long serialVersionUID = -8490149159895201756L;


    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
