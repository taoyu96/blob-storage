package com.bluemyth.storage.blob;

/**
 * blob类型枚举类
 *
 * @author xiaot
 * @date 2020-8-14 12:39
 */
public enum BlobType {

    localfs("localfs"),
    fdfs("fdfs"),
    s3fs("s3fs");

    private String type;

    BlobType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
