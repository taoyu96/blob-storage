package com.bluemyth.storage.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("")
public class VersionController {

    @GetMapping("version")
    public Map<String, String> version(HttpServletRequest request) {
        Map<String, String> versionInfo = new HashMap<>();
        try {
            String text = IOUtils.toString(new URI("classpath:version.txt"), StandardCharsets.UTF_8);
            String[] textArr = text.split("\n");
            for (String line : textArr) {
                if (!line.startsWith("#")) {
                    line = line.replace("\r", "");
                    String[] infos = line.split("=");
                    versionInfo.put(infos[0].trim(), infos[1].trim());
                }
            }
            return versionInfo;
        } catch (Exception e) {
            return versionInfo;
        }
    }
}
