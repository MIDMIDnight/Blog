package com.ccc.blog.controller;

import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping ("currentUser")
    public R currentUser(@RequestHeader("Authorization") String token){
    return sysUserService.findUserByToken(token);
    }
}
