package com.intters.file.ftp;

import com.intters.file.AbstractFile;
import com.intters.util.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author Ruison
 * @date 2019/9/1.
 */
@Slf4j
public class FtpFileUtil extends AbstractFile {

    private AbstractFtp ftp;

    public FtpFileUtil(AbstractFtp ftp) {
        this.ftp = ftp;
    }

    @Override
    public String upload(String fileName, InputStream fileStream, String prefix, CodeEnum codeEnum) {
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String fileNewName = System.currentTimeMillis() + "." + suffix;
        String path = codeEnum.getMsg() + "/" + prefix;
        ftp.upload(path, fileNewName, fileStream);
        String fileUrl = path + "/" + fileNewName;
        log.info("【文件上传】 源文件名称: {}, 上传路径: {}, 上传完成的完整文件路径: {}", fileName, path, fileUrl);
        return fileUrl;
    }
}
