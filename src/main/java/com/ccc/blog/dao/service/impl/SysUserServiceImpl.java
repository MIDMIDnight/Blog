package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.dao.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【ms_sys_user】的数据库操作Service实现
* @createDate 2022-07-31 13:28:46
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findUserByArticles(Long articleID) {
        SysUser sysUser = sysUserMapper.selectById(articleID);
        if (sysUser==null){
            SysUser sysUser1 = new SysUser();
            sysUser1.setNickname("菠萝小刺头");
            return sysUser1;
        }
        return sysUser;
    }
}




