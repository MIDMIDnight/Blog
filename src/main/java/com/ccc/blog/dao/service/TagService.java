package com.ccc.blog.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.blog.dao.pojo.Tag;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.TagVo;


import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_tag】的数据库操作Service
* @createDate 2022-07-31 13:28:54
*/
public interface TagService extends IService<Tag> {
    public List<TagVo> findTagsByArticles(Long articlesID);

    R hots(int limit);

}
