package com.markerhub.service.impl;

import com.markerhub.entity.Article;
import com.markerhub.mapper.ArticleMapper;
import com.markerhub.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oo
 * @since 2021-11-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
