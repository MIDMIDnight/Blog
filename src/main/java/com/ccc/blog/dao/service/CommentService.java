package com.ccc.blog.dao.service;

import com.ccc.blog.vo.common.CommentParam;
import com.ccc.blog.vo.common.R;

public interface CommentService {
    R commentsByArticleId(Long id);

    R comment(CommentParam commentParam);
}
