package com.bluemyth.storage.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许跨域
 * WebFilter 注解可以声明一个过滤器，但是@Order不生效，以filterName的首字母a-z排序执行
 * 另外一种过滤器声明方式，使用过滤器注册器实现 FilterRegistrationBean
 *
 * @author xiaot
 * @version 1.0.0
 * @date 2020-8-22 17:19
 */
@Slf4j
@WebFilter(filterName = "crossFilter", urlPatterns = "/*")
public class CrossFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        //log.debug("CrossFilter -> uri:" + uri);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
