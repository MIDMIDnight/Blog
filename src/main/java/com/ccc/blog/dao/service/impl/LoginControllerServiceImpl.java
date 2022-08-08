package com.ccc.blog.dao.service.impl;

import com.alibaba.fastjson.JSON;
import com.ccc.blog.Utils.JWTUtils;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.LoginControllerService;
import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.vo.common.ErrorCode;
import com.ccc.blog.vo.common.LoginParam;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class LoginControllerServiceImpl implements LoginControllerService {

    private static final String slat = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public R login(LoginParam loginParam) {
/*     思路：1.检查参数是否合法
            2.根据用户名和密码去user表中查询
            3.如果不存在登录失败
            4.如果存在使用jwt生成token返回给前端
            5.token放入redis中，
 */
        String account=loginParam.getAccount();
        String password=loginParam.getPassword();
        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(password)){
            return R.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md2Hex(password + slat);
        SysUser sysUser= sysUserService.finfUser(account,password);
        if (sysUser==null){
            return R.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token= JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return R.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap==null){
            return null;
        }
        String s = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(s)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(s, SysUser.class);
        return sysUser;

    }

    @Override
    public R logout(String token) {
        redisTemplate.delete("TOKEN_"+token);

        return null;
    }
}
