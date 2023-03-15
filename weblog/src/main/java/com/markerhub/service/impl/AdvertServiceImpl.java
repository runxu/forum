package com.markerhub.service.impl;

import com.markerhub.entity.Advert;
import com.markerhub.mapper.AdvertMapper;
import com.markerhub.service.AdvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oo
 * @since 2021-12-23
 */
@Service
public class AdvertServiceImpl extends ServiceImpl<AdvertMapper, Advert> implements AdvertService {
}
