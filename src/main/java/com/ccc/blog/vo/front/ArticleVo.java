package com.ccc.blog.vo.front;


//与前端进行数据交互的返回结果类根据前端设计

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    //private ArticleBodyVo body;

    private List<TagVo> tags;

    //private List<CategoryVo> categorys;


}
