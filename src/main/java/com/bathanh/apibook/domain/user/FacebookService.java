package com.bathanh.apibook.domain.user;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {

    public SocialUser parseToken(final String facebookToken) {
        final Facebook facebook = new FacebookTemplate(facebookToken);
        final User userLogin = facebook.fetchObject("me", User.class, "id", "email", "first_name", "last_name");

        return SocialUser.builder()
                .firstName(userLogin.getFirstName())
                .lastName(userLogin.getLastName())
                .id(userLogin.getId())
                .build();
    }
}