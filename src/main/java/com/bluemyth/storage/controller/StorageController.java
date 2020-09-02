package com.bluemyth.storage.controller;

import com.bluemyth.storage.blob.BlobStorageFactory;
import com.bluemyth.storage.response.ServiceResult;
import com.bluemyth.storage.utils.MimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Slf4j
@Api(value = "存储服务", tags = "存储服务")
@RestController
@RequestMapping("")
public class StorageController {

    @Autowired
    BlobStorageFactory blobStorageFactory;

    @ApiOperation(value = "文档访问代理服务", notes = "文档访问代理服务")
    @GetMapping("proxy/{id}")
    public ResponseEntity<byte[]> proxy(@PathVariable("id") String id) {
        byte[] bytes = blobStorageFactory.getBlobStorage().download(id);
        if (bytes == null) {
            return null;
        }
        String extType = MimeUtil.getExtType(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MimeUtil.getMimeType(extType)));
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "文档下载服务", notes = "文档下载服务")
    @GetMapping("download/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable("id") String id) {
        File file = blobStorageFactory.getBlobStorage().getFile(id);
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }

    @ApiOperation(value = "文档上传服务", notes = "文档上传服务")
    @PostMapping("upload")
    public ServiceResult upload(@RequestParam("file") MultipartFile file, Integer bizType) {
        if (file.isEmpty()) {
            return ServiceResult.ofFailed("上传失败，请选择文件");
        }
        if (bizType == null) {
            bizType = 1000;
        }
        try {
            String id = blobStorageFactory.getBlobStorage().put(file.getBytes(), "", file.getOriginalFilename(), bizType);
            return ServiceResult.ofSuccess(blobStorageFactory.render(id));
        } catch (Exception e) {
            return ServiceResult.ofFailed("上传失败 ", e.getMessage());
        }
    }

    @ApiOperation(value = "文档上传服务(二进制)", notes = "文档上传服务(二进制)")
    @PostMapping("upload1")
    public ServiceResult upload1(@RequestParam("bytes") byte[] bytes, @RequestParam("fileName") String fileName, Integer bizType) {
        if (bytes.length == 0) {
            return ServiceResult.ofFailed("上传失败，文件内容为空");
        }
        if (bizType == null) {
            bizType = 1000;
        }
        try {
            String id = blobStorageFactory.getBlobStorage().put(bytes, "", fileName, bizType);
            return ServiceResult.ofSuccess(blobStorageFactory.render(id));
        } catch (Exception e) {
            return ServiceResult.ofFailed("上传失败 ", e.getMessage());
        }
    }

    @ApiOperation(value = "文档删除服务", notes = "文档上传服务")
    @GetMapping("delete/{id}")
    public ServiceResult delete(@PathVariable("id") String id) {
        boolean flag = blobStorageFactory.getBlobStorage().remove(id);
        return ServiceResult.ofSuccess(flag);
    }
}
