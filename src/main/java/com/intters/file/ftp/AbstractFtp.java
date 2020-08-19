package com.intters.file.ftp;

import com.intters.util.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Ruison
 * on 2019/8/31 - 15:13
 */
public abstract class AbstractFtp implements Closeable {

    public static final Charset DEFAULT_CHARSET = UTF_8;

    protected String host;
    protected int port;
    protected String user;
    protected String password;
    protected Charset charset;

    /**
     * 如果连接超时的话，重新进行连接
     * @since 4.5.2
     *
     * @return this
     */
    public abstract AbstractFtp reconnectIfTimeout();

    /**
     * 打开指定目录
     *
     * @param directory directory
     * @return 是否打开目录
     */
    public abstract boolean cd(String directory);

    /**
     * 打开上级目录
     *
     * @return 是否打开目录
     * @since 4.0.5
     */
    public boolean toParent() {
        return cd("..");
    }

    /**
     * 远程当前目录（工作目录）
     *
     * @return 远程当前目录
     */
    public abstract String pwd();

    /**
     * 在当前远程目录（工作目录）下创建新的目录
     *
     * @param dir 目录名
     * @return 是否创建成功
     */
    public abstract boolean mkdir(String dir);

    /**
     * 文件或目录是否存在
     *
     * @param path 目录
     * @return 是否存在
     */
    public boolean exist(String path) {
        final String fileName = getName(path);
        final String dir = getDir(path, fileName);
        final List<String> names = ls(dir);
        return containsIgnoreCase(names, fileName);
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     *
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public abstract List<String> ls(String path);

    /**
     * 删除指定目录下的指定文件
     *
     * @param path 目录路径
     * @return 是否存在
     */
    public abstract boolean delFile(String path);

    /**
     * 删除文件夹及其文件夹下的所有文件
     *
     * @param dirPath 文件夹路径
     * @return boolean 是否删除成功
     */
    public abstract boolean delDir(String dirPath);

    /**
     * 创建指定文件夹及其父目录，从根目录开始创建，创建完成后回到默认的工作目录
     *
     * @param dir 文件夹路径，绝对路径
     */
    public void mkDirs(String dir) {
        final String[] dirs = StringUtils.trim(dir).split("[\\\\/]+");

        final String now = pwd();
        if(dirs.length > 0 && StringUtils.isEmpty(dirs[0])) {
            //首位为空，表示以/开头
            this.cd(String.valueOf(StringPool.SLASH));
        }
        for (int i = 0; i < dirs.length; i++) {
            if (StringUtils.isNotEmpty(dirs[i])) {
                if (!cd(dirs[i])) {
                    //目录不存在时创建
                    mkdir(dirs[i]);
                    cd(dirs[i]);
                }
            }
        }
        // 切换回工作目录
        cd(now);
    }

    /**
     * 将本地文件上传到目标服务器，目标文件名为destPath，若destPath为目录，则目标文件名将与srcFilePath文件名相同。覆盖模式
     *
     * @param srcFilePath 本地文件路径
     * @param destFile 目标文件
     * @return 是否成功
     */
    public abstract boolean upload(String srcFilePath, File destFile);

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
    public abstract boolean upload(String path, String fileName, InputStream fileStream);

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @param outFile 输出文件或目录
     */
    public abstract void download(String path, File outFile);

    // ---------------------------------------------------------------------------------------------------------------------------------------- Private method start
    /**
     * 是否包含指定字符串，忽略大小写
     *
     * @param names 文件或目录名列表
     * @param nameToFind 要查找的文件或目录名
     * @return 是否包含
     */
    private static boolean containsIgnoreCase(List<String> names, String nameToFind) {
        if (CollectionUtils.isEmpty(names)) {
            return false;
        }
        if (StringUtils.isEmpty(nameToFind)) {
            return false;
        }
        for (String name : names) {
            if (nameToFind.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public String getName(String path) {
        int splitterIndex = path.lastIndexOf("/");
        int suffixIndex = path.lastIndexOf(".");
        if (splitterIndex > 0) {
            return path.substring(splitterIndex + 1, suffixIndex);
        }
        return path.substring(0, suffixIndex);
    }

    public String getDir(String path, String fileName) {
        return path.substring(0, path.indexOf(fileName) - 1);
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IORuntimeException IO异常
     */
    public static File touch(File file) throws IORuntimeException {
        if (null == file) {
            return null;
        }
        if (!file.exists()) {
            mkParentDirs(file);
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new IORuntimeException(e.getMessage());
            }
        }
        return file;
    }

    /**
     * 创建所给文件或目录的父目录
     *
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return parentFile;
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedOutputStream getOutputStream(File file) throws IORuntimeException {
        try {
            return new BufferedOutputStream(new FileOutputStream(touch(file)));
        } catch (Exception e) {
            throw new IORuntimeException(e.getMessage());
        }
    }

    /**
     * 获得输入流
     *
     * @param path Path
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     * @since 4.0.0
     */
    public static BufferedInputStream getInputStream(Path path) throws IORuntimeException {
        try {
            return new BufferedInputStream(Files.newInputStream(path));
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(File file) throws IORuntimeException, FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }
}
