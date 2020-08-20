package com.bluemyth.storage;

import com.bluemyth.storage.blob.BlobConfig;
import com.bluemyth.storage.blob.BlobStorage;
import com.bluemyth.storage.blob.BlobStorageFactory;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StorageTest {

    public static void main(String[] args) throws Exception {

        Map<String, String> configMap = new HashMap<>();
        configMap.put(BlobConfig.BLOB_BLOBTYPE, "localfs");
        configMap.put(BlobConfig.BLOB_LOCALFS_ROOTPATHS, "F:/localfs/");

        BlobStorageFactory factory = new BlobStorageFactory(new BlobConfig(configMap));
        BlobStorage storage = factory.getBlobStorage();

        //String id = storage.put(new File("F:/localfs/1.jpg"));
        //System.out.println(id);
        //System.out.println(storage.getFile("0X3E8/20200814/RjovbG9jYWxmcy8sMFgzRTgvMjAyMDA4MTQvLGpwZywxNTk3MzcyODY3MDE2LDNiYjcxNWZlLTNkZjEtNGJjNC1hZmZiLTMwNWYwNWViOTJlNg==.jpg").getPath());

        // storage.remove("RjpcbG9jYWxmcywwWDNFOC8yMDIwMDgxNCwuanBnLDE1OTczMzQ2ODc1NzM=.jpeg");


        File file = new File("F:/localfs");

        MediaType mediaType = MediaType.parseMediaType("audio/mpeg");
        System.out.println(mediaType);
    }
}
