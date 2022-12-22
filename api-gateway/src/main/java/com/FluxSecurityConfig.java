package com;

import com.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class FluxSecurityConfig {

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private final WebClient.Builder webClientBuilder;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
            ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable());
        return http.authorizeExchange()
                .anyExchange().permitAll()
                .and().formLogin()
                .and().logout()
                .and().build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        System.out.println("================== CHECK CREDENTIALS ==============");
        return (username) ->
                webClientBuilder.build().get()
                .uri("lb://user-service/users/{username}", username)
                .retrieve()
                .bodyToMono(User.class)
                .map(User::new);
    }
}