package com.dewanshu.dropvault.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupCheck {

    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @PostConstruct
    public void init() {
        System.out.println("CLIENT ID = " + clientId);
    }
}