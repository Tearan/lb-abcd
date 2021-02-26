package com.lb.abcd.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @ClassName SwaggerConfig
 * @Description SwaggerConfig配置类
 * @Author Terran
 * @Date 2021/1/29 17:48
 * @Version 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {

        /** 全局参数 */
        Parameter token = new ParameterBuilder().name("authorization")
                .description("用户登陆令牌")
                .parameterType("header")
                .modelRef(new ModelRef("String"))
                /** 是否必须*/
                .required(false)
                .build();
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(token);
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo())
                .enable(enable)
                .groupName("ABCD-平台")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lb.abcd.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ABCD")
                .description("ABCD-平台")
                /** 作者信息*/
                .contact(new Contact("Terran", "https://www.abcd.com/", "929145946@qq.com"))
                .version("1.0")
                .build();
    }
}
