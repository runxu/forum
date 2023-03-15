package com.markerhub.controller;


import com.markerhub.common.lang.Result;
import com.markerhub.entity.Advert;
import com.markerhub.service.AdvertService;
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
public class AdvertController {
    @Autowired
    AdvertService guanggaoService;

    @RequestMapping("/adimg")
    public Result list(){
        List<Advert> list = guanggaoService.list();
        return Result.succ(list);
    }


}
