package com.qiandao.coursedesign.service;

import com.qiandao.coursedesign.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konan
 * @since 2022-06-05
 */
@Transactional
public interface UserService extends IService<User> {

}
