package com.ccc.blog.controller;

import com.ccc.blog.dao.service.LoginControllerService;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginControllerService loginControllerService;

    @GetMapping
    public R logout(@RequestHeader("Authorization") String token){
        return loginControllerService.logout(token);

    }
}
