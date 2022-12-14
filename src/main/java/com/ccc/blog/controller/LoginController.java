package com.ccc.blog.controller;

import com.ccc.blog.dao.service.LoginControllerService;
import com.ccc.blog.vo.common.LoginParam;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginControllerService loginControllerService;

    @PostMapping
    public R login(@RequestBody LoginParam loginParam){

        return loginControllerService.login(loginParam);
    }
}
