package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.LoginControllerService;
import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.dao.mapper.SysUserMapper;
import com.ccc.blog.vo.common.ErrorCode;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.LoginUserVo;
import com.ccc.blog.vo.front.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private LoginControllerService loginControllerService;
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

    @Override
    public SysUser finfUser(String account, String password) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.eq(SysUser::getPassword,password);
        lambdaQueryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        lambdaQueryWrapper.last("limit 1");

        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public R findUserByToken(String token) {
        /*
        * 验证token合法
        *
        * */

        SysUser sysUser=loginControllerService.checkToken(token);
        if (sysUser==null){
            R.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        System.out.println("-------------------------------------"+loginUserVo);
        return R.success(loginUserVo);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
            if (sysUser==null){
                SysUser sysUser1 = new SysUser();

                sysUser1.setNickname("菠萝小刺头");

            }
            UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);

        return userVo;
    }
}




