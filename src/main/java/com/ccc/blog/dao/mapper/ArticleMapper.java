package com.ccc.blog.dao.mapper;

import com.ccc.blog.dao.dos.Archives;
import com.ccc.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_article】的数据库操作Mapper
* @createDate 2022-07-31 13:26:54
* @Entity com.ccc.personblog.dao.pojo.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}




