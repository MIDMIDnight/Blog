package com.ccc.blog.controller;

import com.ccc.blog.vo.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public R test(){
        return R.success(null);
    }
}

