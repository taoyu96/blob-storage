package com.bluemyth.storage.blob;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 对象存储服务
 *
 * @author xiaot
 * @date 2020-8-14 12:39
 */
public interface BlobStorage {

    /**
     * 判断对象是否存储
     *
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * 根据 id 查找对象
     *
     * @param id
     * @return
     */
    Blob findById(String id);

    /**
     * 导入本地文件，并指定对象 id 及文件名称
     *
     * @param localFile 本地文件
     * @throws Exception
     */
    String put(File localFile) throws Exception;

    /**
     * 导入本地文件，并指定对象 id 及文件名称
     *
     * @param localFile 本地文件
     * @param id
     * @throws IOException
     */
    String put(File localFile, String id) throws Exception;

    /**
     * @param file
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    String put(File file, String id, String type) throws Exception;

    /**
     * @param file
     * @param type
     * @param id
     * @param bizType 业务类型
     * @return
     * @throws Exception
     */
    String put(File file, String id, String type, int bizType) throws Exception;

    /**
     * 将输入流导入库中，并指定对象 id 及文件名称
     *
     * @param input 输入流
     * @throws IOException
     */
    String put(InputStream input, String id, String type) throws Exception;

    /**
     * @param input
     * @param type
     * @param id
     * @param bizType
     * @return
     * @throws Exception
     */
    String put(InputStream input, String id, String type, int bizType) throws Exception;


    /**
     * @param inputByte
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    String put(byte[] inputByte, String id, String type) throws Exception;

    /**
     * @param inputByte
     * @param type
     * @param id
     * @param bizType
     * @return
     * @throws Exception
     */
    String put(byte[] inputByte, String id, String type, int bizType) throws Exception;

    /**
     * 删除对象
     *
     * @param id 对象 id
     */
    boolean remove(String id);

    /**
     * 下载对象
     *
     * @param id
     */
    byte[] download(String id);

    /**
     * 获取存储对象唯一id
     *
     * @return
     */
    String getNextId(String type);

    /**
     * 根据传入的唯一序号，获取存储对象唯一id
     *
     * @param type 文件类型
     * @param seq  文件唯一序号
     * @return
     */
    String getNextId(String type, long seq);

    /**
     * 获取存储文件流
     *
     * @param id 文件ID
     * @return
     */
    File getFile(String id);

    /**
     * 获取存储文件流
     *
     * @param id   文件ID
     * @param type 文件类型
     * @return
     */
    File getFile(String id, String type);
}