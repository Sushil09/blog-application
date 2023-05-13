package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class BlogApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleRepository.save(userRole);
    }
}
