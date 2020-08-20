package com.bluemyth.storage.blob.localfs;

import com.bluemyth.storage.blob.StorageException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 本地存储工具
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public class LocalfsUtil {

    public static final long MIN_FREE_SIZE = 1073741824L; // 1GB

    /**
     * 判断所在盘符可用空间是否大于1GB
     * <p>
     * 低于1GB不可用
     *
     * @param file
     * @return
     */
    public static boolean allowMinFreeSize(File file) {
        return file.getFreeSpace() > MIN_FREE_SIZE;
    }

    /**
     * @param id
     * @return
     */
    public static Map<String, Object> decode(String id) {
        try {
            String[] args = id.split("/");
            String fileName = args[args.length - 1];
            String fileId = fileName.split("\\.")[0];

            String msg = new String(Base64.getDecoder().decode(fileId.getBytes()));

            String[] msgArr = msg.split(",");
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("rootPath", msgArr[0]);
            attributes.put("path", msgArr[1]);
            attributes.put("type", msgArr[2]);
            attributes.put("seq", msgArr[3]);

            fileName = isEmptyString(msgArr[2]) ? fileId : fileId + "." + msgArr[2];
            attributes.put("fileId", fileId);
            attributes.put("fileName", fileName);
            attributes.put("id", msgArr[1] + fileName);
            attributes.put("fullPath", msgArr[0] + msgArr[1] + fileName);

            return attributes;
        } catch (Exception e) {
            throw new StorageException("非法本地存储id=" + id);
        }
    }

    /**
     * @param rootPath 根目录  F:/localfs/
     * @param path     相对目录  m1/20200804/
     * @param type     文件类型
     * @param seq      序列号
     * @return
     */
    public static String encode(String rootPath, String path, String type, long seq) {
        StringBuffer sb = new StringBuffer();
        sb.append(rootPath).append(",");
        sb.append(path).append(",");
        sb.append(type).append(",");
        sb.append(seq).append(",");
        sb.append(UUID.randomUUID().toString()); //加入随机码，避免加密后存在不唯一情况
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    /**
     * 文件名 / 文件类型
     *
     * @param type .jpg | a.jpg | a.b.jpg
     * @return eg： jpg
     */
    public static String getFileExtType(String type) {
        if (!isEmptyString(type)) {
            String[] typeArr = type.split("\\.");
            if (typeArr.length > 1) {
                return typeArr[typeArr.length - 1];
            } else {
                return typeArr[0];
            }
        }
        return "";
    }

    /**
     * @param bizType
     * @return
     */
    public static String getBizPath(int bizType) {
        return ("0x" + Integer.toHexString(bizType)).toUpperCase() + "/" + getNowDate() + "/";
    }

    /**
     *
     * @return
     */
    public static String getNowDate() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return str == null || "".equals(str.trim());
    }

    public static void main(String[] args) {
        String rootPath = "F:/localfs/";
        String path = getBizPath(1000);
        String type = getFileExtType("jpg");
        String key = encode(rootPath, path, type, System.currentTimeMillis());
        System.out.println(key);
        //System.out.println(decode(path + "/" + key + ".jpg"));
        System.out.println(decode(key));

        System.out.println(getBizPath(1000));
        System.out.println(Integer.decode("0X3E8"));
    }
}
