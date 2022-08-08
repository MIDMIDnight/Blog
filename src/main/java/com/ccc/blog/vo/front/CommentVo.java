package com.ccc.blog.vo.front;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    //防止前端进度损失改为string
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    private UserVo Author;


    private String content;
    private List<CommentVo> childrens;
    private String CreateDate;
    private Integer level;
    private UserVo toUser;
}
