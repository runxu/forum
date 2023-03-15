package com.markerhub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Category;
import com.markerhub.service.BlogService;
import com.markerhub.service.CategoryService;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oo
 * @since 2021-11-26
 */
@RestController

public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    BlogService blogService;

    @GetMapping("/lunbo")
    public Result lunbo(){

        List<Category> list = categoryService.list();
        return Result.succ(list);
    }

    @GetMapping("/category")
    public Result category(@RequestParam(value = "currentPage")Integer currentPage, @RequestParam(value = "categoryid")Integer categoryid){
        Page page = new Page(currentPage,4);
        IPage created = blogService.page(page,new QueryWrapper<Blog>().eq("categoryid",categoryid).orderByDesc("created"));
        return Result.succ(created);
    }

    @GetMapping("breadxie")
    public Result breadxie(@RequestParam Integer cid){
        Category cid1 = categoryService.getOne(new QueryWrapper<Category>().eq("cid", cid));

        return Result.succ(cid1);

    }

}
