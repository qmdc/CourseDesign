package com.qiandao.coursedesign.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean=>3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        RoleOrFilter roleOrFilter = new RoleOrFilter(); //自定义拦截器
        Map<String, Filter> myFilterMap = new HashMap<>();
        myFilterMap.put("perms",roleOrFilter);//可以配置RoleOrFilter的Bean

        Map<String , String > filterMap = new LinkedHashMap<>();    //拦截

        filterMap.put("/shop/usercenter","perms[user:root|user:primary]");   //用户中心-个人信息
        filterMap.put("/shop/order","perms[user:root|user:primary]");   //提交订单
        filterMap.put("/shop/record","perms[user:root|user:primary]");   //购物记录
        filterMap.put("/shop/recording","perms[user:root|user:primary]");   //购物记录-视图跳转
        filterMap.put("/shop/info","perms[user:root]");
        filterMap.put("/shop/userinfo","perms[user:root]");
        filterMap.put("/shop/addShop","perms[user:root]");
        filterMap.put("/shop/parse/**","perms[user:root]");

        filterMap.put("/shop/**","authc");

        filterMap.put("/user/logout", "logout"); //退出登录

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/userlogin");//没有登录时设置登录请求
        bean.setUnauthorizedUrl("/Unauthorized");   //用户访问没有权限时跳转
        //使用自定义拦截器
        bean.setFilters(myFilterMap);

        return bean;
    }


    //DefaultWebSecurityManager=>2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象=>1
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    //整合ShiroDialect：用来整合 Shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
