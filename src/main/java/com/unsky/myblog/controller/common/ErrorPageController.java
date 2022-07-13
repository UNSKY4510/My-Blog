package com.unsky.myblog.controller.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author UNSKY
 * @date 2022年4月15日 11:22
 */
@Controller
public class ErrorPageController {
    @RequestMapping("error_404")
    public String toPage404(){
        return "error/error_404";
    }

    @RequestMapping("error_400")
    public String toPage400(){
        return "error/error_400";
    }

    @RequestMapping("error_5xx")
    public String toPage5xx(){
        return "error/error_5xx";
    }
}
