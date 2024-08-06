package com.person.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${person.app.url}")
    private String url;

    @Bean
    public OpenAPI getOpenApi() {

        Server server = new Server();
        server.setUrl(url);
        server.setDescription("This url for Local Environment");

        Contact contact = new Contact();
        contact.setEmail("person.appwork@gmail.com");
        contact.setName("Person");
        contact.setUrl("https://www.personproject.com");

        License license = new License().name("Person License").url("https://www.personproject.com/license");

        Info info = new Info();
        info.setTitle("Person Management Api");
        info.setVersion("1.0");
        info.setContact(contact);
        info.setDescription("These api manages person data");
        info.setTermsOfService("https://www.personproject.com/terms");
        info.setLicense(license);

        return new OpenAPI().info(info).servers(List.of(server)).addSecurityItem(
                        new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createApiKeyScheme()));
    }

    private SecurityScheme createApiKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
