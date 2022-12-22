package com.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private final WebClient.Builder webClientBuilder;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/GetCustomerId")
    public Mono<Long> getCustomerId(Principal user)
    {
        if(user == null)
        {
            return null;
        }

        Mono<Long> customerId =  webClientBuilder.build().get()
                .uri("http://user-service/users/getCustomerIdFromUsername/{username}", user.getName())
                .retrieve()
                .bodyToMono(Long.class);

        System.out.println("Customer ID: " + customerId);
        return customerId;
    }
}



