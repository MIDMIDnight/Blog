package com.ccc.blog.dao.mapper;

import com.ccc.blog.dao.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_tag】的数据库操作Mapper
* @createDate 2022-07-31 13:28:54
* @Entity com.ccc.personblog.dao.pojo.Tag
*/

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticles(Long articlesID);

    List<Long> findHotsId(int limit);

    List<Tag> findTagByTagsIds(List<Long> tagsid );

}




