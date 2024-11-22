package com.social.tweetbackend.security.service;

import com.social.tweetbackend.security.model.UserDto;
import com.social.tweetbackend.security.model.Users;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<Users, UserDto> {
    @Override
    public UserDto apply(Users users) {
        return new UserDto(
                users.getFirstName(),
                users.getLastName(),
                users.getEmail(),
                users.getUsername(),
                users.getPassword(),
                users.getPhoneNumber()
        );
    }
}
