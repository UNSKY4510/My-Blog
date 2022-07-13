package com.unsky.myblog.dao;

import com.unsky.myblog.pojo.BlogTag;
import com.unsky.myblog.pojo.BlogTagCount;
import com.unsky.myblog.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author UNSKY
 * @date 2022年4月20日 22:51
 */
@Mapper
public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    BlogTag selectByTagName(String tagName);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> findTagList(PageQueryUtil pageUtil);

    List<BlogTagCount> getTagCount();

    int getTotalTags(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int batchInsertBlogTag(List<BlogTag> tagList);
}
