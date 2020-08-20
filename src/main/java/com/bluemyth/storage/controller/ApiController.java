package com.bluemyth.storage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("")
public class ApiController {

    @GetMapping("version")
    public String version(HttpServletRequest request) {
        log.info("访问 >>{} ", request.getRequestURI());
        return "version : 1.0.0.20200814";
    }

}
