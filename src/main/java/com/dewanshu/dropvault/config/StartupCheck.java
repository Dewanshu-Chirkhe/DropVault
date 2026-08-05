package com.dewanshu.dropvault.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupCheck {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @PostConstruct
    public void check() {
        System.out.println("CLIENT ID = " + clientId);
        System.out.println("SECRET LENGTH = " + clientSecret.length());
        System.out.println("SECRET START = " + clientSecret.substring(0, 6));
    }
}