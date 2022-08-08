package com.ccc.blog.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccc.blog.dao.pojo.Tag;
import com.ccc.blog.dao.service.TagService;
import com.ccc.blog.dao.mapper.TagMapper;
import com.ccc.blog.vo.common.R;
import com.ccc.blog.vo.front.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
* @author Administrator
* @description 针对表【ms_tag】的数据库操作Service实现
* @createDate 2022-07-31 13:28:54
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{
    @Autowired
    private TagMapper  tagMapper;

    @Override
    public List<TagVo> findTagsByArticles(Long articlesID){
        List<Tag> tagsByArticles = tagMapper.findTagsByArticles(articlesID);
        return copyList(tagsByArticles);
    }

    @Override
    public R hots(int limit) {

        List<Long> hotsId = tagMapper.findHotsId(limit);

        if (hotsId==null){
            return R.success(Collections.emptyList());
        }
        List<Tag> findTagByTagsIds=tagMapper.findTagByTagsIds(hotsId);

        return R.success(findTagByTagsIds);
    }

    @Override
    public R findAll() {
        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return R.success(copyList(tags));
    }

    @Override
    public R findAllDetail() {
        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return R.success(copyList(tags));
    }

    @Override
    public R getDetailById(Long id) {

        Tag tag = tagMapper.selectById(id);
        return R.success(copy(tag));
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList=new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

}




