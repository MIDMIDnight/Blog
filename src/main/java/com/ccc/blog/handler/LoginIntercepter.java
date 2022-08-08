package com.ccc.blog.handler;

import com.alibaba.fastjson.JSON;
import com.ccc.blog.Utils.UserThreadLocal;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.LoginControllerService;
import com.ccc.blog.vo.common.ErrorCode;
import com.ccc.blog.vo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class LoginIntercepter implements HandlerInterceptor {
    @Autowired
    private   LoginControllerService loginControllerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //handlermethod ：是判断请求是否是controller层的如果不是直接放行
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        //token
        String authoriization = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", authoriization);
        log.info("=================request end===========================");



        if (StringUtils.isEmpty(authoriization)){
            R fail = R.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        SysUser sysUser = loginControllerService.checkToken(authoriization);
        if (sysUser == null){
            R fail = R.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }

        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
