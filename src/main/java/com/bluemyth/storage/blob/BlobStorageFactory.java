package com.bluemyth.storage.blob;

import com.bluemyth.storage.blob.localfs.LocalfsStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象存储服务工厂
 *
 * @author xiaot
 * @date 2020-8-14 12:39
 */
public class BlobStorageFactory {

    private static Map<String, BlobStorage> container = new ConcurrentHashMap<>();

    private BlobConfig config;

    public BlobStorageFactory(BlobConfig config) {
        this.config = config;
    }

    /**
     * @return
     */
    public BlobStorage getBlobStorage() {
        String currentBlobType = this.config.getConfig().get(BlobConfig.BLOB_BLOBTYPE);
        return getBlobStorage(currentBlobType);
    }

    /**
     * @param blobType
     * @return
     */
    public BlobStorage getBlobStorage(String blobType) {
        if (container.containsKey(blobType)) {
            return container.get(blobType);
        } else {
            BlobStorage blobStorage = null;
            if (BlobType.localfs.getType().equals(blobType)) {
                blobStorage = new LocalfsStorage(config);
            } else if (BlobType.fdfs.getType().equals(blobType)) {
                throw new StorageException("尚未实现存储类型：" + blobType);
            } else if (BlobType.s3fs.getType().equals(blobType)) {
                throw new StorageException("尚未实现存储类型：" + blobType);
            } else {
                throw new StorageException("尚未实现存储类型：" + blobType);
            }

            container.put(blobType, blobStorage);
            return blobStorage;
        }
    }

    public Map<String, String> render(String id) {
        Map<String, String> blobInfo = new HashMap<>();
        String currentBlobType = this.config.getConfig().get(BlobConfig.BLOB_BLOBTYPE);
        if (BlobType.localfs.getType().equals(currentBlobType)) {
            String proxyUrl = this.config.getConfig().get(BlobConfig.BLOB_STORAGE_PROXY_URL);
            String[] args = id.split("/");
            String fileName = args[args.length - 1];
            String fileId = fileName.split("\\.")[0];

            blobInfo.put("fullUrl", proxyUrl + id);
            blobInfo.put("id", id);
            blobInfo.put("fileId", fileId);
            blobInfo.put("fileName", fileName);
        } else {
            blobInfo.put("id", id);
        }

        return blobInfo;
    }
}
