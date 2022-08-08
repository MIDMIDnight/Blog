package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.blog.Utils.UserThreadLocal;
import com.ccc.blog.dao.dos.Archives;
import com.ccc.blog.dao.mapper.ArticleBodyMapper;
import com.ccc.blog.dao.mapper.ArticleMapper;
import com.ccc.blog.dao.mapper.ArticleTagMapper;
import com.ccc.blog.dao.pojo.Article;
import com.ccc.blog.dao.pojo.ArticleBody;
import com.ccc.blog.dao.pojo.ArticleTag;
import com.ccc.blog.dao.pojo.SysUser;
import com.ccc.blog.dao.service.*;
import com.ccc.blog.vo.common.ArticleParam;
import com.ccc.blog.vo.common.PagePram;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.ArticleBodyVo;
import com.ccc.blog.vo.front.ArticleVo;
import com.ccc.blog.vo.front.TagVo;
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

    @Autowired
    private ArticleBodyMapper articleMapper1;

    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
//    @Override
//    public List<ArticleVo> listArticlesPage(PagePram pagePram) {
//        Page<Article> articlePage = new Page(pagePram.getPage(),pagePram.getPageSize());
//
//        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        if (pagePram.getCategoryId()!=null){
//            lambdaQueryWrapper.eq(Article::getCategoryId,pagePram.getCategoryId());
//        }
//        List<Long> articleidlist=new ArrayList<>();
//        if (pagePram.getTagId()!=null){
//        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
//
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pagePram.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleidlist.add(articleTag.getArticleId());
//            }
//            if (articleTags.size() > 0) {
//                // and id in(1,2,3)
//                lambdaQueryWrapper.in(Article::getId,articleidlist);
//            }
//
//        }
//
//        lambdaQueryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//
//
//        Page<Article> articlePage1 = articleMapper.selectPage(articlePage,lambdaQueryWrapper);
//
//
//        List<Article> records = articlePage1.getRecords();//需要copy到VO的返回结果类里面
//        List<ArticleVo> articleVos=copyList(records,true,true);
//
//        return articleVos;
//    }
    @Override
    public List<ArticleVo> listArticlesPage(PagePram pagePram) {
    Page<Article> articlePage = new Page(pagePram.getPage(),pagePram.getPageSize());
   IPage<Article> articleIPage= articleMapper.listArticle(articlePage,
            pagePram.getCategoryId(),
            pagePram.getTagId(),
            pagePram.getYear(),
            pagePram.getMonth());
    List<Article> records = articleIPage.getRecords();
    return copyList(records,true,true);
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
    @Autowired
    private ThreadService threadService;
    @Override
    public R findArticleById(Long articleId) {
        Article article=this.articleMapper.selectById(articleId);
        ArticleVo copy = copy(article, true, true,true,true);
        threadService.updateArticleViewCount(articleMapper,article);
        return R.success(copy);
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

    @Autowired
    private CategoryService categoryService;
    private ArticleVo copy(Article article,boolean istag,boolean isauthor,boolean isbody,boolean isCategory){
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
        if (isbody){
            Long bodyID=article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyID));
        }
        if (isCategory){
            Long categoryID=article.getCategoryId();
            articleVo.setCategoryVo(categoryService.findCategoryById(categoryID));

        }
        return articleVo;
    }

    public ArticleBodyVo findArticleBodyById(Long bodyID) {
        ArticleBody articleBody = articleMapper1.selectById(bodyID);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;

    }

    @Override
    public R publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);//?
        //插入之后才会生成id
        this.articleMapper.insert(article);

        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }

        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return R.success(articleVo);
    }

}




