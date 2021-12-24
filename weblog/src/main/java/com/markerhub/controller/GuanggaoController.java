package com.markerhub.controller;


import com.markerhub.common.lang.Result;
import com.markerhub.entity.Guanggao;
import com.markerhub.service.GuanggaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oo
 * @since 2021-12-23
 */
@RestController
public class GuanggaoController {
    @Autowired
    GuanggaoService guanggaoService;

    @RequestMapping("/adimg")
    public Result guanggao(){
        List<Guanggao> list = guanggaoService.list();
        return Result.succ(list);
    }


}
