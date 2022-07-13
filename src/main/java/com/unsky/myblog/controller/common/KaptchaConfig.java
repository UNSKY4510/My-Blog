package com.unsky.myblog.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;


/**
 * @author UNSKY
 * @date 2022年4月14日 16:22
 */
@Component
public class KaptchaConfig {
    /**
    * @Description:  定义一个captcha配置类
    * @Param: []
    * @return: DefaultKaptcha
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //设置验证码
        //设置边框
        properties.setProperty("kaptcha.border", "yes");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "145");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        properties.setProperty("kaptcha.session.key", "verifyCode");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        //字符间距默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "8");
        // 文本集合，验证码值从此集合中获取
        properties.setProperty("kaptcha.textproducer.char.string","abcde2345678gfynmnpwx");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
