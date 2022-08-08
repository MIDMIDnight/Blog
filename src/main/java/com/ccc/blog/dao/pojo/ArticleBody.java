package com.ccc.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="ms_article_body")
public class ArticleBody{

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
