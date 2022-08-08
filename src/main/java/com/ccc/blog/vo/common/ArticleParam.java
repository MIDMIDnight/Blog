package com.ccc.blog.vo.common;

import com.ccc.blog.vo.front.CategoryVo;
import com.ccc.blog.vo.front.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
