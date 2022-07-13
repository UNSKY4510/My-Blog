package com.unsky.myblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 request.getRequestURL() 返回全路径
 request.getRequestURI() 返回除去host（域名或者ip）部分的路径
 request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
 request.getServletPath() 返回除去host和工程名部分的路径

 request.getRequestURL() http://localhost:8081/admin/login
 request.getRequestURI() /admin/login
 request.getContextPath() /
 request.getServletPath() /admin/login
 */

/**
 * @author UNSKY
 * @date 2022年4月14日 23:33
 */
@Configuration
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if (servletPath.startsWith("/admin") && null == request.getSession().getAttribute("loginUser")){
            request.getSession().setAttribute("errorMsg","请重新登录");
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }else {
            request.getSession().removeAttribute("errorMsg");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
