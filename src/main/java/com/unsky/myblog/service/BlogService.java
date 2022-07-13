package com.unsky.myblog.service;

import com.unsky.myblog.controller.vo.BlogDetailVO;
import com.unsky.myblog.controller.vo.SimpleBlogListVO;
import com.unsky.myblog.pojo.Blog;
import com.unsky.myblog.util.PageQueryUtil;
import com.unsky.myblog.util.PageResult;

import java.util.List;

/**
 * @author UNSKY
 * @date 2022/5/19 22:21
 */
public interface BlogService {
    String saveBlog(Blog blog);

    PageResult getBlogsPage(PageQueryUtil pageUtil);

    Boolean deleteBatch(Integer[] ids);

    int getTotalBlogs();

    /**
    * @Description: 根据id获取详情
    * @author: UNSKY
    * @date: 2022/5/19
    */
    Blog getBlogById(Long blogId);

    /**
    * @Description: 后台修改
    * @author: UNSKY
    * @date: 2022/5/19
    */
    String updateBlog(Blog blog);

    /**
    * @Description: 获取首页文章列表
    * @author: UNSKY
    * @date: 2022/5/19
    */
    PageResult getBlogsForIndexPage(int page);

    /**
    * @Description:  首页侧边栏数据列表 0-点击最多 1-最新发布
    * @author: UNSKY
    * @date: 2022/5/19
    */
    List<SimpleBlogListVO> getBlogListForIndexPage(int type);

    /**
    * @Description: 文章详情
    * @author: UNSKY
    * @date: 2022/5/19
    */
    BlogDetailVO getBlogDetail(Long blogId);

    /**
    * @Description: 根据标签获取文章列表
    * @author: UNSKY
    * @date: 2022/5/19
    */
    PageResult getBlogsPageByTag(String tagName, int page);

    /**
    * @Description: 根据分类获取文章列表
    * @author: UNSKY
    * @date: 2022/5/19
    */
    PageResult getBlogsPageByCategory(String categoryId, int page);

    /**
    * @Description: 根据搜索获取文章列表
    * @author: UNSKY
    * @date: 2022/5/19
    */
    PageResult getBlogsPageBySearch(String keyword, int page);

    BlogDetailVO getBlogDetailBySubUrl(String subUrl);

    List<Blog> selectAll();
}
