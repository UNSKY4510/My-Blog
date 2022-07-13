package com.unsky.myblog.service;

import java.util.Map;

/**
 * @author UNSKY
 * @date 2022年4月16日 15:47
 */
public interface ConfigService {
    /**
    * @Description: 修改配置
    * @Param: ConfigName 配置名称
    * @Param: ConfigValue 值
    * @return: 成功返回执行update语句的数目 失败返回0
    * @author: UNSKY
    * @date: 2022年4月16日
     * @param configName
     * @param configValue
    */
    int updateConfig(String configName, String configValue);

    /**
    * @Description: 获取所有的配置
    * @author: UNSKY
    * @date: 2022年4月16日
    */
    Map<String,String> getAllConfigs();
}
