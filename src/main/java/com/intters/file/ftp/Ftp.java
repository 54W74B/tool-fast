package com.intters.file.ftp;

import com.intters.util.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruison
 * on 2019/8/31 - 14:30
 */
@Slf4j
public class Ftp extends AbstractFtp {
    /** 默认端口 */
    public static final int DEFAULT_PORT = 21;
    private FTPClient client;
    private FtpMode mode;
    /** 执行完操作是否返回当前目录 */
    private boolean backToPwd = true;

    /**
     * 构造
     *
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param charset 编码
     * @param mode 模式
     */
    public Ftp(String host, int port, String user, String password, Charset charset, FtpMode mode) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.charset = charset;
        this.mode = mode;
        this.init();
    }

    /**
     * 初始化连接
     *
     * @return this
     */
    public Ftp init() {
        return this.init(this.host, this.port, this.user, this.password, this.mode);
    }

    /**
     * 初始化连接
     *
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param mode 模式
     * @return this
     */
    public Ftp init(String host, int port, String user, String password, FtpMode mode) {
        final FTPClient client = new FTPClient();
        client.setControlEncoding(StringPool.UTF_8);
        try {
            // 连接ftp服务器
            client.connect(host, port);
            // 登录ftp服务器
            client.login(user, password);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        }
        // 是否成功登录服务器
        final int replyCode = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            try {
                client.disconnect();
            } catch (IOException e) {
                // ignore
            }
            throw new FtpException("Login failed for user [" + user + "], reply code is: [" + replyCode + "]");
        }
        this.client = client;
        if (mode != null) {
            setMode(mode);
        }
        return this;
    }

    /**
     * 设置FTP连接模式，可选主动和被动模式
     *
     * @param mode 模式枚举
     * @return this
     * @since 4.1.19
     */
    public Ftp setMode(FtpMode mode) {
        this.mode = mode;
        switch (mode) {
            case Active:
                this.client.enterLocalActiveMode();
                break;
            case Passive:
                this.client.enterLocalPassiveMode();
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 重连
     */
    public Ftp restart() {
        final FTPClient client = new FTPClient();
        client.setControlEncoding(StringPool.UTF_8);
        try {
            // 连接ftp服务器
            client.connect(host, port);
            // 登录ftp服务器
            client.login(user, password);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        }
        this.client = client;
        if (mode != null) {
            setMode(mode);
        }
        log.info("FTP 重连成功");
        return this;
    }

    /**
     * 设置执行完操作是否返回当前目录
     *
     * @param backToPwd 执行完操作是否返回当前目录
     * @return this
     * @since 4.6.0
     */
    public Ftp setBackToPwd(boolean backToPwd) {
        this.backToPwd = backToPwd;
        return this;
    }

    /**
     * 如果连接超时的话，重新进行连接 经测试，当连接超时时，client.isConnected()仍然返回ture，无法判断是否连接超时 因此，通过发送pwd命令的方式，检查连接是否超时
     *
     * @return this
     */
    @Override
    public Ftp reconnectIfTimeout() {
        String pwd = null;
        try {
            pwd = pwd();
        } catch (FtpException fex) {
            // ignore
        }

        if (pwd == null) {
            return this.init();
        }
        return this;
    }

    /**
     * 改变目录
     *
     * @param directory 目录
     * @return 是否成功
     */
    @Override
    public boolean cd(String directory) {
        if (StringUtils.isEmpty(directory)) {
            return false;
        }

        boolean flag;
        try {
            flag = client.changeWorkingDirectory(directory);
        } catch (IOException e) {
            try {
                flag = restart().client.changeWorkingDirectory(directory);
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        }
        return flag;
    }

    /**
     * 远程当前目录
     *
     * @return 远程当前目录
     * @since 4.1.14
     */
    @Override
    public String pwd() {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            try {
                return restart().client.printWorkingDirectory();
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        }
    }

    @Override
    public List<String> ls(String path) {
        final FTPFile[] ftpFiles = lsFiles(path);

        final List<String> fileNames = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            fileNames.add(ftpFile.getName());
        }
        return fileNames;
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 目录
     * @return 文件或目录列表
     */
    public FTPFile[] lsFiles(String path) {
        String pwd = null;
        if (StringUtils.isNotEmpty(path)) {
            pwd = pwd();
            cd(path);
        }

        FTPFile[] ftpFiles;
        try {
            ftpFiles = this.client.listFiles();
        } catch (IOException e) {
            try {
                ftpFiles = restart().client.listFiles();
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        } finally {
            // 回到原目录
            cd(pwd);
        }

        return ftpFiles;
    }

    @Override
    public boolean mkdir(String dir) {
        boolean flag;
        try {
            flag = this.client.makeDirectory(dir);
        } catch (IOException e) {
            try {
                flag = restart().client.makeDirectory(dir);
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        }
        return flag;
    }

    /**
     * 判断ftp服务器文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    public boolean existFile(String path) {
        FTPFile[] ftpFileArr;
        try {
            ftpFileArr = client.listFiles(path);
        } catch (IOException e) {
            try {
                ftpFileArr = restart().client.listFiles(path);
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        }
        return ftpFileArr != null && ftpFileArr.length > 0;
    }

    @Override
    public boolean delFile(String path) {
        final String pwd = pwd();
        final String fileName = getName(path);
        final String dir = getDir(path, fileName);
        cd(dir);
        boolean isSuccess;
        try {
            isSuccess = client.deleteFile(fileName);
        } catch (IOException e) {
            try {
                isSuccess = restart().client.deleteFile(fileName);
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        } finally {
            // 回到原目录
            cd(pwd);
        }
        return isSuccess;
    }

    @Override
    public boolean delDir(String dirPath) {
        FTPFile[] dirs;
        try {
            dirs = client.listFiles(dirPath);
        } catch (IOException e) {
            try {
                dirs = restart().client.listFiles(dirPath);
            } catch (IOException ex) {
                throw new FtpException(ex.getMessage());
            }
        }
        String name;
        String childPath;
        for (FTPFile ftpFile : dirs) {
            name = ftpFile.getName();
            childPath = dirPath + "/" + name;
            if (ftpFile.isDirectory()) {
                // 上级和本级目录除外
                if (!name.equals(".") && !name.equals("..")) {
                    delDir(childPath);
                }
            } else {
                delFile(childPath);
            }
        }

        // 删除空目录
        try {
            return this.client.removeDirectory(dirPath);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        }
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param file 文件
     * @return 是否上传成功
     */
    @Override
    public boolean upload(String path, File file) {
        Assert.notNull(file, "file to upload is null !");
        return upload(path, file.getName(), file);
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     * @param file 文件
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName 自定义在服务端保存的文件名
     * @return 是否上传成功
     */
    public boolean upload(String path, String fileName, File file) {
        try (InputStream in = getInputStream(file)) {
            return upload(path, fileName, in);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        }
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * <pre>
     * 1. path为null或""上传到当前路径
     * 2. path为相对路径则相对于当前路径的子路径
     * 3. path为绝对路径则上传到此路径
     * </pre>
     *
     *
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName 文件名
     * @param fileStream 文件流
     * @return 是否上传成功
     */
    @Override
    public boolean upload(String path, String fileName, InputStream fileStream) {
        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            try {
                restart().client.setFileType(FTPClient.BINARY_FILE_TYPE);
            } catch (IOException ex) {
                throw new FtpException(e.getMessage());
            }
        }

        String pwd = null;
        if (this.backToPwd) {
            pwd = pwd();
        }

        if (StringUtils.isNotEmpty(path)) {
            mkDirs(path);
            boolean isOk = cd(path);
            if (!isOk) {
                return false;
            }
        }

        try {
            return client.storeFile(fileName, fileStream);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        } finally {
            if (this.backToPwd) {
                cd(pwd);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @param outFile 输出文件或目录
     */
    @Override
    public void download(String path, File outFile) {
        final String fileName = getName(path);
        final String dir = getDir(path, fileName);
        download(dir, fileName, outFile);
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @param fileName 文件名
     * @param outFile 输出文件或目录
     */
    public void download(String path, String fileName, File outFile) {
        if (outFile.isDirectory()) {
            outFile = new File(outFile, fileName);
        }
        if (!outFile.exists()) {
            touch(outFile);
        }
        try (OutputStream out = getOutputStream(touch(outFile))) {
            download(path, fileName, out);
        } catch (IOException e) {
            throw new FtpException(e.getMessage());
        }
    }

    /**
     * 下载文件到输出流
     *
     * @param path 文件路径
     * @param fileName 文件名
     * @param out 输出位置
     */
    public void download(String path, String fileName, OutputStream out) {
        String pwd = null;
        if (this.backToPwd) {
            pwd = pwd();
        }

        cd(path);
        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.retrieveFile(fileName, out);
        } catch (IOException e) {
            try {
                restart();
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                client.retrieveFile(fileName, out);
            } catch (IOException ex) {
                throw new FtpException(ex.getMessage());
            }
        } finally {
            if (backToPwd) {
                cd(pwd);
            }
        }
    }

    /**
     * 获取FTPClient客户端对象
     *
     * @return {@link FTPClient}
     */
    public FTPClient getClient() {
        return client;
    }

    @Override
    public void close() throws IOException {
        if (null != this.client) {
            this.client.logout();
            if (this.client.isConnected()) {
                this.client.disconnect();
            }
            this.client = null;
        }
    }
}
