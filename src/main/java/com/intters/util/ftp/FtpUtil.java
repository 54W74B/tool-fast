package com.intters.util.ftp;

import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpConfig;
import cn.hutool.extra.ftp.FtpMode;
import com.intters.exception.Assert;
import com.intters.util.StringPool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * FTP 工具类扩展
 *
 * @author Ruison
 * @date 2020/9/10-14:47
 */
public class FtpUtil extends Ftp {

    public FtpUtil(String host) {
        super(host);
    }

    public FtpUtil(String host, int port) {
        super(host, port);
    }

    public FtpUtil(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    public FtpUtil(String host, int port, String user, String password, Charset charset) {
        super(host, port, user, password, charset);
    }

    public FtpUtil(String host, int port, String user, String password, Charset charset, FtpMode mode) {
        super(host, port, user, password, charset, mode);
    }

    public FtpUtil(FtpConfig config, FtpMode mode) {
        super(config, mode);
    }

    /**
     * 上传文件
     *
     * @param destPath   服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param customPath 自定义路径
     * @param file       文件
     * @return 上传路径
     */
    public String uploadFile(String destPath, String customPath, File file) {
        // 上传路径
        String uploadPath = getUploadPath(destPath, customPath);
        return upload(uploadPath, file) ? uploadPath + StringPool.SLASH + file.getName() : null;
    }

    /**
     * 上传文件
     *
     * @param destPath      服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param customPath    自定义路径
     * @param multipartFile 文件
     * @return 上传路径
     * @throws IOException IO异常
     */
    public String uploadFile(String destPath, String customPath, MultipartFile multipartFile) throws IOException {
        // 文件原始名称
        String originalFilename = multipartFile.getOriginalFilename();
        Assert.checkNull(originalFilename, "[tool-fast] 上传文件的名称为空");
        String[] filenames = originalFilename.split(StringPool.BACK_SLASH + StringPool.DOT);
        Assert.checkBoolean(filenames.length == 2, "[tool-fast] 上传文件格式有误，文件名称：" + originalFilename);
        // 新建临时文件
        File file = File.createTempFile(filenames[0], StringPool.DOT + filenames[1]);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        // 上传路径
        String uploadPath = getUploadPath(destPath, customPath);
        boolean uploadResult = upload(uploadPath, file);
        String filename = file.getName();
        // 删除文件
        file.deleteOnExit();
        return uploadResult ? uploadPath + StringPool.SLASH + filename : null;
    }

    /**
     * 获取上传路径
     *
     * @param destPath   服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param customPath 自定义路径
     * @return 上传路径
     */
    public static String getUploadPath(String destPath, String customPath) {
        return StringUtils.isBlank(customPath) ? destPath : destPath + StringPool.SLASH + customPath;
    }
}
