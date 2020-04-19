package com.zph.springbootadvanced.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getSession().getId());
        if(request.getSession().getAttribute("user")==null) {
            System.out.println("被拦截器拦截了");
            response.sendRedirect("/login");
            return false;
        }
        System.out.println("没有拦截");
        return true;
    }
}
