package com.ccc.blog.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccc.blog.dao.pojo.Article;
import com.ccc.blog.vo.common.PagePram;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.ArticleVo;


import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_article】的数据库操作Service
* @createDate 2022-07-31 13:26:54
*/
public interface ArticleService extends IService<Article> {

    List<ArticleVo> listArticlesPage(PagePram pagePram);

    R hotArticle(int limit);

    R newArticle(int limt);

    R listArchives();
}
