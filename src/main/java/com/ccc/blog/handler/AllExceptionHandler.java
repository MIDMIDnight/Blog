package com.ccc.blog.handler;

import com.ccc.blog.vo.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//aop
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//以json形式传给前端
    public R doException(Exception exception){
        exception.printStackTrace();
        return R.fail(-999,"服务器内部出现错误");
    }
}
