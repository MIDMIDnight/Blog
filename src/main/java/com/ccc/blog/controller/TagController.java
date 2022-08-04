package com.ccc.blog.controller;


import com.ccc.blog.dao.service.TagService;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
