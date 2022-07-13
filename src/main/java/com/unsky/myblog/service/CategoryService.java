package com.unsky.myblog.service;

import com.unsky.myblog.pojo.BlogCategory;
import com.unsky.myblog.util.PageQueryUtil;
import com.unsky.myblog.util.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author UNSKY
 * @date 2022年4月27日 00:03
 */
public interface CategoryService {
    /**
     * 查询分类的分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getBlogCategoryPage(PageQueryUtil pageUtil);

    int getTotalCategories();

    /**
     * 添加分类数据
     *
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    Boolean saveCategory(String categoryName,String categoryIcon);

    Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon);

    Boolean deleteBatch(Integer[] ids);

    List<BlogCategory> getAllCategories();
}
