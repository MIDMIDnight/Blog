package com.ccc.blog.dao.service;

import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryID);

    R findAll();

    R findAllDetail();

    R categorieDetailById(Long id);
}
