package com.klasha.code.challenge.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.klasha.code.challenge.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.klasha.code.challenge.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Klasha Code Challenge API Documentation")
                .description("Apis documentation for all exposed endpoints")
                .version("1.0.0")
                .build();
    }
}

