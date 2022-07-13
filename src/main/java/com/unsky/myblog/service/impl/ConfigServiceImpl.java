package com.unsky.myblog.service.impl;

import com.unsky.myblog.dao.BlogConfigMapper;
import com.unsky.myblog.pojo.BlogConfig;
import com.unsky.myblog.service.ConfigService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author UNSKY
 * @date 2022年4月16日 16:05
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private BlogConfigMapper blogConfigMapper;

    public static final String websiteName = "personal blog";
    public static final String websiteDescription = "记录我的学习以及生活";
    public static final String websiteLogo = "/admin/dist/img/mylogo.jpg";
    public static final String websiteIcon = "/admin/dist/img/blogicon.ico";

    public static final String yourAvatar = "/admin/dist/img/mylogo.png";
    public static final String yourEmail = "451056770@qq.com";
    public static final String yourName = "UNSKY";

    public static final String footerAbout = "UNSKY's blog";
    public static final String footerICP = "浙ICP备2022000243号-1";
    public static final String footerCopyRight = "UNSKY";
    public static final String footerPoweredBy = "personal blog";
    public static final String footerPoweredByURL = "##";


    @Override
    public int updateConfig(String configName, String configValue) {
        BlogConfig blogConfig = blogConfigMapper.selectByPrimaryKey(configName);
        if (blogConfig!=null){
            blogConfig.setConfigValue(configValue);
            blogConfig.setUpdateTime(new Date());
            return blogConfigMapper.updateByPrimaryKeySelective(blogConfig);
        }
        return 0;
    }

    @Override
    public Map<String, String> getAllConfigs() {
        // 获取所有的map并且封装为map
        List<BlogConfig> blogConfigs = blogConfigMapper.selectAll();
        Map<String,String> configMap = blogConfigs.stream().collect(Collectors.toMap(BlogConfig::getConfigName, BlogConfig::getConfigValue));
        for (Map.Entry<String, String> config : configMap.entrySet()) {
            if ("websiteName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteName);
            }
            if ("websiteDescription".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteDescription);
            }
            if ("websiteLogo".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteLogo);
            }
            if ("websiteIcon".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(websiteIcon);
            }
            if ("yourAvatar".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourAvatar);
            }
            if ("yourEmail".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourEmail);
            }
            if ("yourName".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(yourName);
            }
            if ("footerAbout".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerAbout);
            }
            if ("footerICP".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerICP);
            }
            if ("footerCopyRight".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerCopyRight);
            }
            if ("footerPoweredBy".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredBy);
            }
            if ("footerPoweredByURL".equals(config.getKey()) && StringUtils.isEmpty(config.getValue())) {
                config.setValue(footerPoweredByURL);
            }
        }
        return configMap;
    }
}
