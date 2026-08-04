package com.dewanshu.dropvault.service;

import com.dewanshu.dropvault.entity.User;
import com.dewanshu.dropvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveGoogleUser(OAuth2User oauthUser) {

        String googleId = oauthUser.getAttribute("sub");

        return userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {

                    User user = User.builder()
                            .googleId(googleId)
                            .email(oauthUser.getAttribute("email"))
                            .name(oauthUser.getAttribute("name"))
                            .profilePicture(oauthUser.getAttribute("picture"))
                            .createdAt(LocalDateTime.now())
                            .build();

                    return userRepository.save(user);

                });
    }

}