package com.markerhub.service.impl;

import com.markerhub.entity.Category;
import com.markerhub.mapper.CategoryMapper;
import com.markerhub.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oo
 * @since 2021-11-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
