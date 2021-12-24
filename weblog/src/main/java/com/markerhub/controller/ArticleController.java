package com.markerhub.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Article;
import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.markerhub.service.ArticleService;
import com.markerhub.service.BlogService;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oo
 * @since 2021-11-24
 */
@RestController

public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userservice;

    @Autowired
    BlogService blogService;


    @RequestMapping("/article")
    public Result article(@RequestParam(value = "id")Integer id,@RequestParam("artcommentid")Integer artid, @RequestParam("content")String content,@RequestParam("commentnum")Integer commentnum){

        Article article = new Article();
        article.setArtUserId(id);
        article.setArtCommentId(artid);
        article.setArtcontent(content);
        DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时 mm分 ss秒");
        article.setArtcreatedtime(dfDate.format((LocalDateTime.now())));
        User user = userservice.getOne(new QueryWrapper<User>().eq("id", id));
        article.setUsername(user.getUsername());
        article.setAvatar(user.getAvatar());
        boolean save = articleService.save(article);

        if(save == true){
            commentnum += 1;
            boolean update = blogService.update(new UpdateWrapper<Blog>().eq("id", artid).set("commentnum", commentnum));
        }

        return Result.succ(save);

    }

    @GetMapping("/comment")
    public Result comment(@RequestParam(value = "blogid") Integer blogid){

        List<Article> artComment = articleService.list(new QueryWrapper<Article>().eq("artCommentId", blogid));


        return Result.succ(artComment);


    }
    @GetMapping("/mycomment")
    public Result mycomment(@RequestParam(defaultValue = "1", value = "currentPage") Integer currentPage, @RequestParam("userId") Integer userId){
        Page page = new Page(currentPage.intValue(), 4L);
        IPage artUserId = articleService.page(page, (Wrapper) ((QueryWrapper) (new QueryWrapper()).eq("artUserId", userId)));

        return Result.succ(artUserId);


    }



}
