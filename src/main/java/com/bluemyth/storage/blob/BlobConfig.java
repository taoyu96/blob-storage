package com.bluemyth.storage.blob;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储配置
 *
 * @author xiaot
 * @date 2020-8-14 12:39
 */
public class BlobConfig {

    public Map<String, String> config = new HashMap<>();

    public BlobConfig() {
    }

    public BlobConfig(Map<String, String> config) {
        this.config = config;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }


    //配置key声明
    public static final String BLOB_BLOBTYPE = "blobType";  //当前存储类型
    public static final String BLOB_STORAGE_PROXY_URL = "proxy-url"; //访问代理地址
    public static final String BLOB_LOCALFS_ROOTPATHS = "localfs.root-paths";


}
