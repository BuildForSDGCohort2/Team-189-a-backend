package com.team189.backend.chama;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author moha
 */
@SpringBootApplication
@EnableSwagger2
@Configuration
public class ChamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChamaApplication.class, args);
	}
  @Autowired
    private Environment environment;

    @Bean
    public Docket api() {
        boolean swaggerswitch = environment.getRequiredProperty("swagger.enable") != null
                && environment.getRequiredProperty("swagger.enable").equals("true") ? true : false;
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.team189.backend.chama.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .apiInfo(apiInfo()).enable(swaggerswitch);

    }
   

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Backend",
                "Microservice",
                "v1.0.0",
                "Terms of service",
                "polycarpmogaka16@gmail.com",
                "License of API",
                "#");
        return apiInfo;
    }

        

}
