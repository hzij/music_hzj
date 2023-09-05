package com.hzj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@Slf4j
public class OnlineMusicPlaybackApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineMusicPlaybackApplication.class, args);
        log.info("项目启动成功");
    }

}
