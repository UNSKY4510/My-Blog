package com.unsky.myblog.interceptor;

import com.unsky.myblog.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author UNSKY
 * @date 2022年4月14日 23:49
 */
@Configuration
public class SecurityInterceptor implements WebMvcConfigurer {
    @Resource
    private AdminLoginInterceptor adminLoginInterceptor;
    /**
    * @Description: 添加一个拦截器，拦截/admin/为前缀的URL
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor).
                //addPathPatterns()中传必须要拦截的路径
                addPathPatterns("/admin/**").
                //excludePathPatterns()中传不想拦截的路径
                excludePathPatterns("/admin/login").
                excludePathPatterns("/admin/dist/**").
                excludePathPatterns("/admin/plugins/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
