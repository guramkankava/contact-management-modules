package com.github.guramkankava.configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(1)
    SecurityFilterChain asFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

        http.exceptionHandling((e) -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("bill").password("password").roles("USER").build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    RegisteredClientRepository registeredClientRepository() {
      RegisteredClient contactManager =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("contactManager")
            .clientSecret("secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientSettings(ClientSettings.builder().requireProofKey(false).build())
            .tokenSettings(TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.ofHours(1)).build())
            .redirectUri("http://localhost:8081/authorized")
            .scope(OidcScopes.OPENID)
            .build();

      return new InMemoryRegisteredClientRepository(contactManager);
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey).
                privateKey(privateKey).
                keyID(UUID.randomUUID().toString()).
                build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
      return AuthorizationServerSettings.builder().build();
    }
}