package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccc.blog.dao.pojo.Category;
import com.ccc.blog.dao.mapper.CategoryMapper;
import com.ccc.blog.dao.service.CategoryService;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long categoryID) {
        Category category = categoryMapper.selectById(categoryID);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public R findAll() {
        List<Category> categories=categoryMapper.selectList(new LambdaQueryWrapper<>());
        return R.success(copyList(categories));
    }

    @Override
    public R findAllDetail() {
        List<Category> categories=categoryMapper.selectList(new LambdaQueryWrapper<>());
        return R.success(copyList(categories));
    }

    @Override
    public R categorieDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return R.success(copy(category));
    }

    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        //id不一致要重新设立
        //categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

}
