package com.lb.abcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.lb.abcd")
public class LbSeparateApplication {

    public static void main(String[] args) {
        SpringApplication.run(LbSeparateApplication.class, args);
    }

}
