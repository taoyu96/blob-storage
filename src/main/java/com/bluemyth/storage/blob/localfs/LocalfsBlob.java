package com.bluemyth.storage.blob.localfs;

import com.bluemyth.storage.blob.AbstractBlob;

import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Blob 的 本地文件系统 实现类
 *
 * @author xiaot
 * @date 2020-8-14 12:38
 */
public class LocalfsBlob extends AbstractBlob {

    private File localFile;

    LocalfsBlob(String id) {
        super(id, id);
        Map<String, Object> attributes = LocalfsUtil.decode(id);
        this.localFile = new File((String) attributes.get("fullPath"));
        this.setSize(localFile.getTotalSpace());
        this.setLastModifyDate(new Date(localFile.lastModified()));
        this.setContentType(localFile.getName().split("\\.")[1]);
    }

    @Override
    public void write(OutputStream output) throws IOException {
        InputStream input = null;
        try {
            byte[] buffer = new byte[1024];
            input = new FileInputStream(this.localFile);
            int len;
            while (-1 != (len = input.read(buffer, 0, buffer.length))) {
                output.write(buffer, 0, len);
            }
        } finally {
            try {
                if (input != null) input.close();
            } catch (Exception e) {
            }
        }
    }

    boolean exists() {
        return this.localFile.exists();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.localFile);
    }

    @Override
    public String getContainer() {
        return localFile.getPath();
    }

    public File getLocalFile(){
        return localFile;
    }
}
