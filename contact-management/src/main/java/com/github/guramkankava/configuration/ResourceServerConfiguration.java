package com.github.guramkankava.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.github.guramkankava.property.ResourceServerProperties;
import com.github.guramkankava.service.AuthenticationService;
import com.github.guramkankava.service.SecurityContextHolderAuthenticationService;

@Configuration
public class ResourceServerConfiguration {

    private final ResourceServerProperties resourceServerProperties;

    public ResourceServerConfiguration(ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(c -> c.jwt(j -> j.jwkSetUri(resourceServerProperties.getJwkSetUri())));
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();
            c.anyRequest().authenticated();
        });
        return http.build();
    }

    @Bean
    AuthenticationService securityContextHolderAuthenticationService() {
        return new SecurityContextHolderAuthenticationService();
    }
}
