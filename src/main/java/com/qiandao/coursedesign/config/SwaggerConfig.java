package com.qiandao.coursedesign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {

        //设置要显示的Swagger环境
        Profiles profiles= Profiles.of("dev");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境中,当是线上环境时，.enable(flag)比较智能
        //当监测到当前的环境名与上面设置的`dev`相等时flag为真
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)  //是否启动swagger，false不能启动，默认为true关闭
                .groupName("千岛")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qiandao.coursedesign"))
                //.paths():过滤什么路径
//                .paths(PathSelectors.ant("/konan/**"))
                .build();
    }

    //配置Swagger信息 apiInfo
    private ApiInfo apiInfo() {
        Contact contact = new Contact("万城之湖", "#", "notre1024@icloud.com");
        return new ApiInfo("千岛的SwaggerAPI文档",
                "小草呀，你的足步虽小，但是你拥有你足下的士地",
                "2.9",
                "https://spring.io",
                contact,
                "我的心向着阑珊的风张了帆，要到无论何处的荫凉之岛去～",
                "https://www.grabient.com",
                new ArrayList());
    }


}
