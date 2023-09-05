package com.hzj.onlinemusicplayback.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * PackageName :com.hzj.config
 * ClassName: WebConfig
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/4  17:56
 * @edition 1.0
 */
@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}