package com.github.guramkankava.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfiguration {

    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(info()).
                addSecurityItem(securityRequirement()).
                components(components());
    }

    private Info info() {
        return new Info().title("Contact management REST API").
        description("API for contact management").
        version("1.0").
        contact(contact());
    }

    private Contact contact() {
        return new Contact().name("Guram kankava").
        email("kankava.gurami@gmail.com").
        url("https://github.com/guramkankava");
    }

    private SecurityRequirement securityRequirement () {
        return new SecurityRequirement().addList(BEARER_AUTH);
    }

    private Components components() {
        return new Components().addSecuritySchemes(BEARER_AUTH, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(BEARER_AUTH)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
