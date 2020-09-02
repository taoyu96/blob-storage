package com.bluemyth.storage.blob.localfs;

import com.bluemyth.storage.blob.Blob;
import com.bluemyth.storage.blob.BlobConfig;
import com.bluemyth.storage.blob.BlobStorage;
import com.bluemyth.storage.blob.StorageException;

import java.io.*;


/**
 * 对象存储服务的 本地文件系统 实现类
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public class LocalfsStorage implements BlobStorage {

    final static int READ_BUFFER_SIZE = 1024;

    BlobConfig config;

    public LocalfsStorage(BlobConfig config) {
        this.config = config;
    }

    public String getRootPath() {
        //获取根目录，可配置多个根目录
        String rootPaths = this.config.getConfig().get(BlobConfig.BLOB_LOCALFS_ROOTPATHS);
        if (rootPaths == null || rootPaths.trim().isEmpty()) {
            throw new StorageException("本地储存目录为空，请配置！");
        }

        for (String rootPath : rootPaths.split(",")) {
            if (LocalfsUtil.allowMinFreeSize(new File(rootPath))) { //所在盘低于1GB 则存储下一个根目录
                if (!rootPath.endsWith("/") && !rootPath.endsWith("\\") && !rootPath.endsWith("\\\\")) {
                    rootPath += "/";
                }
                return rootPath;
            }
        }
        throw new StorageException("本地储存目录不足1GB!!! rootPaths:" + rootPaths);
    }

    /**
     * 判断对象是否存储
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(String id) {
        return new LocalfsBlob(id).exists();
    }

    /**
     * 根据 id 查找对象
     *
     * @param id
     * @return
     */
    @Override
    public Blob findById(String id) {
        LocalfsBlob blob = new LocalfsBlob(id);
        if (blob.exists()) {
            return blob;
        }
        return null;
    }

    /**
     * 导入本地文件，并指定对象 id 及文件名称
     *
     * @param file 本地文件
     * @throws Exception
     */
    @Override
    public String put(File file) throws Exception {
        return put(file, "");
    }

    /**
     * 导入本地文件，并指定对象 id 及文件名称
     *
     * @param file 本地文件
     * @param id
     * @throws IOException
     */
    @Override
    public String put(File file, String id) throws Exception {
        return put(file, id, "", -1);
    }

    /**
     * @param file
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String put(File file, String id, String type) throws Exception {
        return put(file, id, type, -1);
    }

    /**
     * @param file
     * @param type
     * @param id
     * @param bizType 业务类型
     * @return
     * @throws Exception
     */
    @Override
    public String put(File file, String id, String type, int bizType) throws Exception {
        if (bizType <= 0) bizType = 1000;
        type = LocalfsUtil.getFileExtType(file.getName());
        String rootPath = getRootPath();
        String path = LocalfsUtil.getBizPath(bizType);
        String fileId = LocalfsUtil.encode(rootPath, path, type, System.currentTimeMillis());
        id = path + fileId + (LocalfsUtil.isEmptyString(type) ? "" : "." + type);

        File dir = new File(rootPath + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileInputStream input = new FileInputStream(file);
        FileOutputStream output = null;
        try {
            byte[] bytes = new byte[READ_BUFFER_SIZE];
            output = new FileOutputStream(new File(rootPath + id));
            int index;
            while ((index = input.read(bytes)) != -1) {
                output.write(bytes, 0, index);
                output.flush();
            }
        } finally {
            if (input != null) {
                output.close();
            }
            if (output != null) {
                output.close();
            }
        }
        return id;
    }

    /**
     * 将输入流导入库中，并指定对象 id 及文件名称
     *
     * @param input 输入流
     * @throws IOException
     */
    @Override
    public String put(InputStream input, String id, String type) throws Exception {
        return put(input, id, type, -1);
    }

    /**
     * @param input
     * @param type
     * @param id
     * @param bizType
     * @return
     * @throws Exception
     */
    @Override
    public String put(InputStream input, String id, String type, int bizType) throws Exception {
        if (bizType <= 0) bizType = 1000;
        type = LocalfsUtil.getFileExtType(type);
        String rootPath = getRootPath();
        String path = LocalfsUtil.getBizPath(bizType);

        String fileId = LocalfsUtil.encode(rootPath, path, type, System.currentTimeMillis());
        id = path + fileId + (LocalfsUtil.isEmptyString(type) ? "" : "." + type);

        File dir = new File(rootPath + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileOutputStream output = null;
        try {
            byte[] bytes = new byte[READ_BUFFER_SIZE];
            output = new FileOutputStream(new File(rootPath + id));
            int index;
            while ((index = input.read(bytes)) != -1) {
                output.write(bytes, 0, index);
                output.flush();
            }
        } finally {
            if (input != null) {
                output.close();
            }
            if (output != null) {
                output.close();
            }
        }

        return id;
    }


    /**
     * @param bytes
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public String put(byte[] bytes, String id, String type) throws Exception {
        return put(bytes, id, type, -1);
    }

    /**
     * @param bytes
     * @param type
     * @param id
     * @param bizType
     * @return
     * @throws Exception
     */
    @Override
    public String put(byte[] bytes, String id, String type, int bizType) throws Exception {
        OutputStream output = null;
        try {
            if (bizType <= 0) bizType = 1000;
            type = LocalfsUtil.getFileExtType(type);
            String rootPath = getRootPath();
            String path = LocalfsUtil.getBizPath(bizType);
            String fileId = LocalfsUtil.encode(rootPath, path, type, System.currentTimeMillis());
            id = path + fileId + (LocalfsUtil.isEmptyString(type) ? "" : "." + type);

            File dir = new File(rootPath + path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(rootPath + id);
            output = new FileOutputStream(file);
            output.write(bytes);
            output.flush();
        } finally {
            if (output != null) {
                output.close();
            }
        }
        return id;
    }

    /**
     * 删除对象
     *
     * @param id 对象 id
     */
    @Override
    public boolean remove(String id) {
        File file = getFile(id);
        if (file != null) {
            return file.delete();
        }
        return false;
    }

    /**
     * 下载对象
     *
     * @param id
     */
    @Override
    public byte[] download(String id) {
        File file = getFile(id);
        if (file != null) {
            byte[] bytes = null;
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                byte[] b = new byte[READ_BUFFER_SIZE];
                int index;
                while ((index = fis.read(b)) != -1) {
                    bos.write(b, 0, index);
                }
                bytes = bos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                } catch (Exception e) {

                }
            }
            return bytes;
        }
        return null;
    }

    /**
     * 获取存储对象唯一id
     *
     * @return
     */
    @Override
    public String getNextId(String type) {
        return null;
    }

    /**
     * 根据传入的唯一序号，获取存储对象唯一id
     *
     * @param type 文件类型
     * @param seq  文件唯一序号
     * @return
     */
    @Override
    public String getNextId(String type, long seq) {
        return null;
    }

    /**
     * 获取存储文件流
     *
     * @param id 文件ID
     * @return
     */
    @Override
    public File getFile(String id) {
        LocalfsBlob blob = (LocalfsBlob) this.findById(id);
        if (blob != null) {
            return blob.getLocalFile();
        }
        return null;
    }

    /**
     * 获取存储文件流
     *
     * @param id   文件ID
     * @param type 文件类型
     * @return
     */
    @Override
    public File getFile(String id, String type) {
        return getFile(id);
    }

}
