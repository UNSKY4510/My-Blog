package com.unsky.myblog.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.unsky.myblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author UNSKY
 * @date 2022年4月14日 19:16
 */
@Controller
public class CommonController {

    /*验证码失效时间 1分钟*/
    private static final int VALID_CODE_CACHE_EXPIRE_TIME = 5;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private RedisUtil redisUtil;

    /**
    * @Description: 定义获取验证码的请求接口，生成验证码
    * @Param: request http请求
    * @Param: response http响应
    * @return: void
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 用字节数组存储
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();

        try {
            // 生产验证码字符串并保存到session中
            String verifyCode = captchaProducer.createText();
            request.getSession().setAttribute("verifyCode", verifyCode);
            // 生成redis key
            String key = String.format("login_valid_code_%s",request.getSession().getId());
            redisUtil.set(key,verifyCode,VALID_CODE_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            /*使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中*/
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /*定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组*/
        captchaOutputStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
