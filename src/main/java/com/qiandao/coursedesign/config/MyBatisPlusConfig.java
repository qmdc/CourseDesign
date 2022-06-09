package com.qiandao.coursedesign.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.qiandao.coursedesign.mapper")  // 扫描我们的 mapper 文件夹
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    // 注册乐观锁和分页插件(新版)
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));//分页查询组件

        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); //乐观锁插件

        return interceptor;
    }

}
