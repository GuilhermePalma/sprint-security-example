package com.guilhermepalma.springsecurityexample;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = @Server(url = "http://localhost:8080", description = "Local Java Server"),
        info = @Info(title = "Spring Security Example API", version = "1.0",
                contact = @Contact(name = "Guilherme Palma", email = "guippalma@gmail.com", url = "github.com/GuilhermePalma"),
                description = "Example APP using Spring Security, Authentication, Authorization and JWT"))
public class SpringSecurityExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityExampleApplication.class, args);
    }

}
