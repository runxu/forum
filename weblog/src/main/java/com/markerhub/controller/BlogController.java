package com.markerhub.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Thumbs;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.ThumbsService;
import com.markerhub.service.UserService;
import com.markerhub.util.ShiroUtil;
import java.time.LocalDateTime;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @Autowired
    ThumbsService thumbsService;

    @GetMapping({"/blogs"})
    public Result list(@RequestParam(defaultValue = "1", value = "currentPage") Integer currentPage, @RequestParam("userId") Integer userId) {
        Page page = new Page(currentPage.intValue(), 5L);
        IPage created = this.blogService.page((IPage)page, (Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", userId)).orderByDesc("created"));
        if (created.getTotal() < 1L) {
            Page page2 = new Page(1L, 1L);
            IPage created2 = this.blogService.page((IPage)page2, (Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", Integer.valueOf(-1))).orderByDesc("created"));
            return Result.succ(created2);
        }
        return Result.succ(created);
    }
    @GetMapping({"/mytiezi"})
    public Result mytiezi(@RequestParam(defaultValue = "1", value = "currentPage") Integer currentPage, @RequestParam("userId") Integer userId) {
        Page page = new Page(currentPage.intValue(), 4L);
        IPage created = this.blogService.page((IPage)page, (Wrapper)((QueryWrapper)(new QueryWrapper()).eq("user_id", userId)).orderByDesc("created"));
        return Result.succ(created);
    }


    @GetMapping({"/blog/{id}"})
    public Result list1(@PathVariable(name = "id") Long id) {

        Blog blog = (Blog)this.blogService.getById(id);
        return Result.succ(blog);
    }

    @GetMapping({"/blog/{id}/user_id/{userid}"})
    public Result list(@PathVariable(name = "id") Long id, @PathVariable(name = "userid") Long userid) {


        Blog blog = (Blog)this.blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        Thumbs one = (Thumbs)this.thumbsService.getOne((Wrapper)((QueryWrapper)(new QueryWrapper()).eq("blog_id", id)).eq("user_id", userid));
        if (one == null)
            return Result.succ(blog);
        Integer isclick = one.getIsclick();
        blog.setIsclick(isclick);
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping({"/blog/edit"})
    public Result list(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if (blog.getId() != null) {
            temp = (Blog)this.blogService.getById(blog.getId());
            Assert.isTrue((temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue()), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(Integer.valueOf(0));
        }

                BeanUtil.copyProperties(blog, temp, new String[] { "id", "userId", "created", "status" });
        this.blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    @GetMapping({"/sheet"})
    public Result sheetlist(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage.intValue(), 5L);
        IPage created = this.blogService.page((IPage)page, (Wrapper)(new QueryWrapper()).orderByDesc("created"));
        return Result.succ(created);
    }

    @GetMapping({"/hot"})
    public Result hot(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage.intValue(), 5L);
        IPage created = this.blogService.page((IPage)page, (Wrapper)(new QueryWrapper()).orderByDesc("thumbs"));
        return Result.succ(created);
    }

    @GetMapping({"/search"})
    public Result searchlist(@RequestParam(defaultValue = "1", value = "currentPage") Integer currentPage, @RequestParam("keyword") String keyword) {
        Page page = new Page(currentPage.intValue(), 5L);
        IPage created = this.blogService.page((IPage)page, (Wrapper)((QueryWrapper)(new QueryWrapper()).like("title", keyword)).orderByDesc("created"));

        return Result.succ(created);
    }

    @GetMapping({"/blogEditor/{id}"})
    public Result blogEditor(@PathVariable(name = "id") Long id){

        User u = userService.getOne(new QueryWrapper<User>().eq("id", id));
        return Result.succ(u.getUsername());
    }


}
