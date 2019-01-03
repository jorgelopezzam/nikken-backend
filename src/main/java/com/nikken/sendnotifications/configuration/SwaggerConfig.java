package com.nikken.sendnotifications.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {

        Contact contact = new Contact("STARK Team", "http://www.bcp.com.pe", "stark@bcp.com.pe");

        ApiInfo apiInfo = new ApiInfo(
                "Stark API",
                "Here we have the live document for Stark API.",
                "0.0.0",
                "http://www.bcp.com.pe",
                contact,
                "Property of BCP",
                "http://www.bcp.com.pe");
        return apiInfo;
    }
}
