package com.hgh.interceptor;

import com.hgh.context.UserContext;
import com.hgh.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求路径
        String requestURI = request.getRequestURI();

        //2.判断请求路径是否是登录接口
//        if(requestURI.contains("/login")){
//            log.info("登录接口,放行");
//            return true;
//        }

        //3.获取请求头中的token
        String token = request.getHeader("token");

        //4.判断token是否存在
        if(token==null|| token.isEmpty()){
            log.info("令牌不存在,拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //5.判断token是否有效
        try {
            Claims claims = JwtUtils.parseJwt(token);
            Object id = claims.get("id");
            if (id != null) {
                UserContext.setEmpId(Integer.valueOf(id.toString()));
            }
        } catch (Exception e) {
            log.info("令牌解析失败,拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //6.放行请求
        log.info("令牌有效,放行");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束后清理 ThreadLocal，防止线程池复用时数据串用
        UserContext.clear();
    }
}