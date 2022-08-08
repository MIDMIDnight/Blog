package com.ccc.blog.dao.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.UserVo;

/**
* @author Administrator
* @description 针对表【ms_sys_user】的数据库操作Service
* @createDate 2022-07-31 13:28:46
*/
public interface SysUserService extends IService<SysUser> {


    SysUser findUserByArticles(Long articleID);


    SysUser finfUser(String account, String password);

    R findUserByToken(String token);

    UserVo findUserVoById(Long authorId);
}
