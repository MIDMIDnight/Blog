package com.ccc.blog.controller;

import com.ccc.blog.Utils.QiniuUtils;
import com.ccc.blog.vo.common.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping
    public R upload(@RequestParam("image")MultipartFile file){
        //原始文件名称
        String originalFileName=file.getOriginalFilename();
        String name = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFileName, ".");
        boolean upload = qiniuUtils.upload(file, name);
        if (upload){
            return R.success("http://"+QiniuUtils.url+name);
        }else {
            return R.fail(20001,"上传失败");
        }
    }
}
