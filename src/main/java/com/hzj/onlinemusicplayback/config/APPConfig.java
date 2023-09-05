package com.hzj.onlinemusicplayback.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * PackageName :com.hzj.onlinemusicplayback.config
 * ClassName: APPConfig
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  12:56
 * @edition 1.0
 */
@Configuration
@Slf4j
public class APPConfig implements WebMvcConfigurer {
    //登录拦截器
    LoginInterceptor loginInterceptor = new LoginInterceptor();
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/js/**.js")
                //排除images下所有的元素
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/css/**.css")
                .excludePathPatterns("/fronts/**")
                .excludePathPatterns("/player/**")
                .excludePathPatterns("/login.html")
//                排除登录接口
                .excludePathPatterns("/user/login");

    }

}
