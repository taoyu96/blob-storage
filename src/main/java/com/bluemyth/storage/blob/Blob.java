package com.bluemyth.storage.blob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 存储对象
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public interface Blob {

    enum Type {
        BMP, JPG, JPEG, PNG, GIF, MP4, TF, CSV, XLS, XLSX, DOC, DOCX, PPT, PPTX, OTHER
    }

    /**
     * 获取对象id
     */
    String getId();

    /**
     * 获取对象名称
     */
    String getName();

    /**
     * 获取对象存储的容器，如果是本地文件，则获取对象存储路径
     */
    String getContainer();

    /**
     * 获取对象的文档类型
     */
    String getContentType();

    /**
     * 获取对象的文档大小
     */
    long getSize();

    /**
     * 获取最后修改时间
     */
    Date getLastModifyDate();

    /**
     * 获取设置的过期时间
     */
    Date getExpireDate();

    /**
     * 设置过期时间
     */
    void expires(Date date);

    /**
     * 文档内容输出到流
     */
    void write(OutputStream output) throws IOException;

    /**
     * 读取文档内容的输入流
     */
    InputStream getInputStream() throws IOException;

}