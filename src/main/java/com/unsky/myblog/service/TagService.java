package com.unsky.myblog.service;

import com.unsky.myblog.pojo.BlogTagCount;
import com.unsky.myblog.util.PageQueryUtil;
import com.unsky.myblog.util.PageResult;

import java.util.List;

/**
 * @author UNSKY
 * @date 2022年4月20日 21:58
 */
public interface TagService {

    /**
    * @Description:  查询标签的分页数据
    * @Param:
    * @param pageUtil
    * @author: UNSKY
    * @date: 2022年4月20日
    */
    PageResult getBlogTagPage(PageQueryUtil pageUtil);

    /**
    * @Description:  获取所有的tag
    * @Param:
    * @return: int
    * @author: UNSKY
    * @date: 2022年4月20日
    */
    int getTotalTags();

    /**
    * @Description:  保存标签名
    * @Param:
    * @param tagName 标签名
    * @return: java.lang.Boolean
    * @author: UNSKY
    * @date: 2022年4月20日
    */
    Boolean saveTag(String tagName);

    /**
    * @Description:
    * @Param:
    * @param ids
    * @author: UNSKY
    * @date: 2022年4月20日
    */
    Boolean deleteBatch(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();
}
