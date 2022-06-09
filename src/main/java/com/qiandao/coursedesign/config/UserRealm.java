package com.qiandao.coursedesign.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiandao.coursedesign.pojo.User;
import com.qiandao.coursedesign.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");
        Subject subject = SecurityUtils.getSubject();   //拿到当前登录的这个对象
        User principal = (User) subject.getPrincipal(); //拿到User对象

        info.addStringPermission(principal.getPerm());//设置当前用户的权限
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName",userToken.getUsername());
        User user = userService.getOne(wrapper);

        if (user==null) {
            return null;    //抛出异常
        }

        return new SimpleAuthenticationInfo(user,user.getUserPwd(),user.getUserName());
    }
}

