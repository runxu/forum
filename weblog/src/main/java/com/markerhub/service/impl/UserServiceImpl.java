package com.markerhub.service.impl;

import com.markerhub.entity.User;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oo
 * @since 2021-11-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
