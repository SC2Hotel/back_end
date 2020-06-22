package com.example.hotel.interceptor;

import com.example.hotel.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qin
 * @date 2020-06-22
 */

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("nju-token");
        if(token!=null){
            Integer userId = JwtUtil.verifyTokenAndGetUserId(token);
            if(userId!=null){
                return true;
            }
        }
        return false;
    }
}
