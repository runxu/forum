package com.markerhub.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.markerhub.common.lang.Result;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.markerhub.entity.Thumbs;
import com.markerhub.service.BlogService;
import com.markerhub.service.ThumbsService;
import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ThumbsController {
    @Autowired
    BlogService blogService;

    @Autowired
    ThumbsService thumbsService;


    @GetMapping({"/thumbsada"})
    public Result thumbsadd(@RequestParam("num") Integer num, @RequestParam("id") Integer id, @RequestParam("isclick") Integer isclick, @RequestParam("userid") Integer userid) {
        boolean update = this.blogService.update((Wrapper)((UpdateWrapper)(new UpdateWrapper()).eq("id", id)).set("thumbs", num));
        Thumbs thumbs = new Thumbs();
        thumbs.setBlogId(id);
        thumbs.setUserId(userid);
        thumbs.setIsclick(isclick);
        boolean b = this.thumbsService.saveOrUpdate(thumbs, (Wrapper)((UpdateWrapper)((UpdateWrapper)(new UpdateWrapper()).eq("user_id", userid)).eq("blog_id", id)).set("isclick", isclick));
        Thumbs one = (Thumbs)this.thumbsService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper(thumbs)).eq("user_id", userid)).eq("blog_id", id));
        return Result.succ(one);
    }

    @GetMapping({"/thumbs"})
    public Result thumbs(@RequestParam("num") Integer num, @RequestParam("id") Integer id, @RequestParam("isclick") Integer isclick, @RequestParam("userid") Integer userid) {
        boolean update = this.blogService.update((Wrapper)((UpdateWrapper)(new UpdateWrapper()).eq("id", id)).set("thumbs", num));
        Thumbs thumbs = new Thumbs();
        thumbs.setBlogId(id);
        thumbs.setUserId(userid);
        thumbs.setIsclick(isclick);
        boolean b = this.thumbsService.saveOrUpdate(thumbs, (Wrapper)((UpdateWrapper)((UpdateWrapper)(new UpdateWrapper()).eq("user_id", userid)).eq("blog_id", id)).set("isclick", isclick));
        Thumbs one = (Thumbs)this.thumbsService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper(thumbs)).eq("user_id", userid)).eq("blog_id", id));
        return Result.succ(one);
    }
}

