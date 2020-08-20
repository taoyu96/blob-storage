package com.bluemyth.storage.config;

import com.bluemyth.storage.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author xiaot
 * @version 2019-08-13 11:04
 * Copyright (c)2019 pcitech
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionConfig {

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ServiceResult allExceptionHandler(HttpServletRequest request, Exception exception) {
        log.error("请求异常：uri = " + request.getRequestURI(), exception);
        return ServiceResult.ofFailed("请求失败！", exception.getMessage());
    }


}