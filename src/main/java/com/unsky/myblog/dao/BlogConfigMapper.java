package com.unsky.myblog.dao;

import com.unsky.myblog.pojo.BlogConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author UNSKY
 * @date 2022年4月16日 15:58
 */
@Mapper
public interface BlogConfigMapper {
    /**
    * @Description: 返回所有的博客配置
    * @author: UNSKY
    * @date: 2022年4月16日
    */
    List<BlogConfig> selectAll();

    /**
    * @Description:  查询博客的配置
    * @Param: configName 配置名
    * @author: UNSKY
    * @date: 2022年4月16日
    */
    BlogConfig selectByPrimaryKey(String configName);

    /**
    * @Description: 更新博客配置
    * @return: 成功返回update语句的数量  失败返回0
    * @author: UNSKY
    * @date: 2022年4月16日
    */
    int updateByPrimaryKeySelective(BlogConfig record);

}
