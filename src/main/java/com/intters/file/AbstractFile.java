package com.intters.file;


import com.intters.util.enums.CodeEnum;

import java.io.InputStream;

/**
 * @author Ruison
 * @date 2019/9/1.
 */
public abstract class AbstractFile {

    public static AbstractFile FILE = null;

    public AbstractFile() {
    }

    public static AbstractFile init(AbstractFile file) {
        FILE = file;
        return FILE;
    }

    public static AbstractFile getInstance() {
        if (FILE == null) {
            FILE = new AbstractFile() {
                @Override
                public String upload(String fileName, InputStream fileStream, String prefix, CodeEnum codeEnum) {
                    return null;
                }
            };
        }
        return FILE;
    }

    public abstract String upload(String fileName, InputStream fileStream, String prefix, CodeEnum codeEnum);
}
