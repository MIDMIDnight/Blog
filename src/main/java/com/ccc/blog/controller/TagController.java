package com.ccc.blog.controller;


import com.ccc.blog.common.aop.LogAnnotation;
import com.ccc.blog.dao.service.TagService;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public R hots(){
    int limit=6;//查询最热的6个标签
        return tagService.hots(limit);

    }

    @GetMapping
    public R getAll(){

        return tagService.findAll();
    }

    @GetMapping("detail")
    public R getAllDetail(){

        return tagService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public R getDetailById(@PathVariable("id")Long id){

        return tagService.getDetailById(id);
    }
}
