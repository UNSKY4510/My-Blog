package com.unsky.myblog.controller.common;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author UNSKY
 * @date 2022年4月15日 12:04
 */
@Configuration
public class ErrorPageConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,"/error_400");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error_404");
                ErrorPage errorPage5xx = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error_5xx");
                factory.addErrorPages(errorPage400,errorPage404,errorPage5xx);
            }
        };
    }
}
