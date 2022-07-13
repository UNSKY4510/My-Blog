package com.unsky.myblog.service.impl;

import com.unsky.myblog.controller.vo.SimpleBlogListVO;
import com.unsky.myblog.dao.BlogMapper;
import com.unsky.myblog.dao.BlogTagMapper;
import com.unsky.myblog.dao.BlogTagRelationMapper;
import com.unsky.myblog.pojo.Blog;
import com.unsky.myblog.pojo.BlogTag;
import com.unsky.myblog.pojo.BlogTagCount;
import com.unsky.myblog.service.TagService;
import com.unsky.myblog.util.PageQueryUtil;
import com.unsky.myblog.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UNSKY
 * @date 2022年4月20日 23:03
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private BlogTagRelationMapper relationMapper;

    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageUtil) {
        List<BlogTag> tags = blogTagMapper.findTagList(pageUtil);
        int total = blogTagMapper.getTotalTags(pageUtil);
        PageResult pageResult = new PageResult(tags, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalTags() {
        return blogTagMapper.getTotalTags(null);
    }

    @Override
    public Boolean saveTag(String tagName) {
        BlogTag temp = blogTagMapper.selectByTagName(tagName);
        if (temp == null) {
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insertSelective(blogTag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        //已存在关联关系不删除
        List<Long> relations = relationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }
        //删除tag
        return blogTagMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return blogTagMapper.getTagCount();
    }
}
