package com.lb.abcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@EnableJpaRepositories
@EntityScan("com.lb.abcd.**.entity")
@SpringBootApplication(scanBasePackages = "com.lb.abcd")
public class LbSeparateApplication {

    public static void main(String[] args) {
        SpringApplication.run(LbSeparateApplication.class, args);
    }

}
