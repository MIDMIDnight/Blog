package com.ccc.blog.controller;

import com.ccc.blog.common.aop.LogAnnotation;
import com.ccc.blog.dao.service.CommentService;
import com.ccc.blog.vo.common.CommentParam;
import com.ccc.blog.vo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {
    //评论功能要好好学一下特别是数据库
    //这边的业务逻辑不是很懂

    @Autowired

    private CommentService commentService;


    @GetMapping ("article/{id}")
    public R comment(@PathVariable("id")Long id ){


    return commentService.commentsByArticleId(id);
    }


    @PostMapping("create/change")
    public R comment(@RequestBody CommentParam commentParam){
        return commentService.comment(commentParam);
    }

}
