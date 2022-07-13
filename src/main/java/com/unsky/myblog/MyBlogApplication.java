package com.unsky.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyBlogApplication {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyBlogApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MyBlogApplication.class, args);
    }

}
