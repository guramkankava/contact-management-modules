package com.github.guramkankava.service;

import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AuthenticationService {

    Authentication getAuthentication();

}
