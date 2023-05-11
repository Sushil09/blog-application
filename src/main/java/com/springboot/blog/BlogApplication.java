package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog Posting REST APIs",
                description = "Spring Boot Blog Posting REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Sushil Jaiswal",
                        email = "sushil_official@outlook.com",
                        url = "https://github.com/Sushil09"
                ),
                license = @License(name = "Apache 2.0",
                        url = "https://github.com/Sushil09")
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog API Documentation",
                url = "https://github.com/Sushil09"
        )
)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
