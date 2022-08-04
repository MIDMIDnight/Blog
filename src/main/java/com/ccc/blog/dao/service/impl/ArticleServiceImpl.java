package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.blog.dao.dos.Archives;
import com.ccc.blog.dao.mapper.ArticleMapper;
import com.ccc.blog.dao.pojo.Article;
import com.ccc.blog.dao.service.ArticleService;
import com.ccc.blog.dao.service.SysUserService;
import com.ccc.blog.dao.service.TagService;
import com.ccc.blog.vo.common.PagePram;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.ArticleVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


/**
* @author Administrator
* @description 针对表【ms_article】的数据库操作Service实现
* @createDate 2022-07-31 13:26:54
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<ArticleVo> listArticlesPage(PagePram pagePram) {
        Page<Article> articlePage = new Page(pagePram.getPage(),pagePram.getPageSize());

        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);


        Page<Article> articlePage1 = articleMapper.selectPage(articlePage,lambdaQueryWrapper);


        List<Article> records = articlePage1.getRecords();//需要copy到VO的返回结果类里面
        List<ArticleVo> articleVos=copyList(records,true,true);

        return articleVos;
    }

    @Override
    public R hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //select id,title from article order by view_counts desc limt 5
        return  R.success(copyList(articles,false,false));
    }

    @Override
    public R newArticle(int limt) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limt);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //select id,title from article order by create_time  desc limt 5
        return  R.success(copyList(articles,false,false));

    }

    @Override
    public R listArchives() {
       List<Archives> listArchives=  articleMapper.listArchives();
        return R.success(listArchives);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean istag,boolean isauthor) {
    List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,istag,isauthor));
        }
        return articleVoList;
    }
    private ArticleVo copy(Article article,boolean istag,boolean isauthor ){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (istag){
            Long ArticleID=article.getId();
            articleVo.setTags(tagService.findTagsByArticles(ArticleID));
        }
        if (isauthor){
            Long ArticleID=article.getId();
            articleVo.setAuthor(sysUserService.findUserByArticles(ArticleID).getNickname());
        }
        return articleVo;
    }

}




