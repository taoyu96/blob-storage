package com.bluemyth.storage.blob;

/**
 * 存储异常
 *
 * @author xiaot
 * @date 2020-8-14 12:39
 */
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 3980531619058154219L;

    public StorageException(String message) {
        super(message);
    }
}
