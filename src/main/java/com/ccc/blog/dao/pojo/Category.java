package com.ccc.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="ms_category")
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
