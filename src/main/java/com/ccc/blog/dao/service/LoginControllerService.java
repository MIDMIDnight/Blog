package com.ccc.blog.dao.service;

import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.vo.common.LoginParam;
import com.ccc.blog.vo.common.R;
import org.springframework.stereotype.Service;



public interface LoginControllerService {
    R login(LoginParam loginParam);

    SysUser checkToken(String token);

    R logout(String token);
}
