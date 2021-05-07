/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * @author Mauro Sousa
 */
@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("ag.fuel"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(info());
    }

    private ApiInfo info() {
        return new ApiInfoBuilder().title("Coding Evaluation")
                .description("Code Evaluation using Java, Spring boot, MongoDB and Swagger")
                .contact(new Contact("Mauro Sousa", "https://github.com/tuxoPT",
                        "mauro.sousa@fuel.ag"))
                .version("1.0.1")
                .license("Company Wesite")
                .licenseUrl("https://fuel.ag")
                .build();
    }
}
