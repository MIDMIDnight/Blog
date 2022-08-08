package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccc.blog.Utils.UserThreadLocal;
import com.ccc.blog.dao.mapper.CommentMapper;
import com.ccc.blog.dao.pojo.Comment;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.CommentService;
import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.vo.common.CommentParam;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.CommentVo;
import com.ccc.blog.vo.front.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public R commentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos= copyList(comments);
        return R.success(commentVos);
    }

    @Override
    public R comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return R.success(null);

    }

    private List<CommentVo> copyList(List<Comment> comments) {
        ArrayList<CommentVo> commentVos = new ArrayList<>();
        for (Comment c:comments) {
            commentVos.add(copy(c));
        }
        return commentVos;
    }

    private CommentVo copy(Comment c) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(c,commentVo);
        //作者信息
        Long authorId = c.getAuthorId();
        UserVo userVoById = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVoById);
        //子评论
        Integer level= commentVo.getLevel();
        if (level == 1){
            Long id=c.getId();
            List<CommentVo> commentVoList=findCommentsByParamId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to user 给谁评论
        if (level > 1){
            Long toUid=c.getToUid();
            UserVo userVoById1 = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById1);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParamId(Long id) {
    LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getParentId,id);
    queryWrapper.eq(Comment::getLevel,2);
    return copyList(commentMapper.selectList(queryWrapper));
    }
}
