package com.qiandao.coursedesign.service.impl;

import com.qiandao.coursedesign.pojo.User;
import com.qiandao.coursedesign.mapper.UserMapper;
import com.qiandao.coursedesign.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konan
 * @since 2022-06-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
