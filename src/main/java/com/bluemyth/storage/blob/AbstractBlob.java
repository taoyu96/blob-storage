package com.bluemyth.storage.blob;

import java.util.Date;

/**
 * 存储对象抽象类
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public abstract class AbstractBlob implements Blob {
    /**
     * 对象 id
     */
    private String id;

    /**
     * 对象文件名称
     */
    private String name;


    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 保存容器
     */
    private String container;

    public AbstractBlob(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void expires(Date date) {
        this.expireDate = date;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public Date getLastModifyDate() {
        return this.lastModifyDate;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Date getExpireDate() {
        return this.expireDate;
    }

    @Override
    public String getContainer() {
        return container;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContainer(String container) {
        this.container = container;
    }

}
