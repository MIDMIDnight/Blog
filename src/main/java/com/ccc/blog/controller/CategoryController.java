package com.ccc.blog.controller;

import com.ccc.blog.common.aop.LogAnnotation;
import com.ccc.blog.dao.service.CategoryService;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public R categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public R categoriesDetail(){
        return categoryService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public R categorieDetailById(@PathVariable("id") Long ID){
        return categoryService.categorieDetailById(ID);
    }
}
