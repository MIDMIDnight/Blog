package com.ccc.blog.controller;




import com.ccc.blog.common.aop.LogAnnotation;
import com.ccc.blog.dao.service.ArticleService;
import com.ccc.blog.vo.common.ArticleParam;
import com.ccc.blog.vo.common.PagePram;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//restfull
@RequestMapping("articles")

public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping()
    public R showArticlesList(@RequestBody PagePram pagePram){
        List<ArticleVo> articleVos = articleService.listArticlesPage(pagePram);

        R success = R.success(articleVos);

        return success;
    }

    @PostMapping("hot")
    public R showArticleHot(){
        int limt=6;
      return articleService.hotArticle(limt);
    }

    @PostMapping("new")
    public R showArticleNew(){
        int limt=6;
        return articleService.newArticle(limt);
    }


    @PostMapping("listArchives")
    public R listArchives(){

        return articleService.listArchives();
    }
    @PostMapping("view/{id}")
    public R findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);

    }
    @PostMapping("publish")
    public R publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }

}
