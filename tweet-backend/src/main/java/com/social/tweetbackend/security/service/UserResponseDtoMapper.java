package com.social.tweetbackend.security.service;

import com.social.tweetbackend.security.model.UserResponseDto;
import com.social.tweetbackend.security.model.Users;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseDtoMapper implements Function<Users, UserResponseDto> {
    @Override
    public UserResponseDto apply(Users users) {
        return new UserResponseDto(
                users.getFirstName(),
                users.getLastName(),
                users.getEmail(),
                users.getUsername(),
                users.getPhoneNumber()
        );
    }
}
