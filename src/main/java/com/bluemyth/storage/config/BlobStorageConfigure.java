package com.bluemyth.storage.config;

import com.bluemyth.storage.blob.BlobConfig;
import com.bluemyth.storage.blob.BlobStorageFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobStorageConfigure {

    @Bean(name = "blobConfig")
    @ConfigurationProperties(prefix = "blob-storage")
    public BlobConfig blobConfig(){
        return new BlobConfig();
    }

    @Bean
    public BlobStorageFactory blobStorageFactory(){
        return new BlobStorageFactory(blobConfig());
    }

}
