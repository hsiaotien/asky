package com.ceres.logdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LogDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogDemoApplication.class, args);
        log.debug("debug记录日志");
        log.info("info记录日志");
    }

}

