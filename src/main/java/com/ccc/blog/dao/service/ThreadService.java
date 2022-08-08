package com.ccc.blog.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccc.blog.dao.mapper.ArticleBodyMapper;
import com.ccc.blog.dao.mapper.ArticleMapper;
import com.ccc.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {


    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
    int viewCount=article.getViewCounts();
    Article articleupdate=new Article();
    articleupdate.setViewCounts(viewCount+1);
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,viewCount);
        int update = articleMapper.update(articleupdate, queryWrapper);

    }
}
