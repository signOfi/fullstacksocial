package com.social.tweetbackend.security.service;

import com.social.tweetbackend.security.model.UserPrincipal;
import com.social.tweetbackend.security.model.Users;
import com.social.tweetbackend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user1 = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User name not found!") );

        return new UserPrincipal(user1);
    }

}
