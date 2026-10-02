package com.hgh.filter;

import com.hgh.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取请求路径
        String requestURI = request.getRequestURI();

        //2.判断请求路径是否是登录接口
        if(requestURI.contains("/login")){
            log.info("登录接口,放行");
            filterChain.doFilter(request, response);
            return;
        }

        //3.获取请求头中的token
        String token = request.getHeader("token");

        //4.判断token是否存在
        if(token==null|| token.isEmpty()){
            log.info("令牌不存在,拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //5.判断token是否有效
        try {
            JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.info("令牌解析失败,拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //6.放行请求
        log.info("令牌有效,放行");
        filterChain.doFilter(request, response);
    }
}
