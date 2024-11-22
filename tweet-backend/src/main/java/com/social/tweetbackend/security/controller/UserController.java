package com.social.tweetbackend.security.controller;

import com.social.tweetbackend.security.model.*;
import com.social.tweetbackend.security.repository.UserRepository;
import com.social.tweetbackend.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserNameById(@PathVariable Long userId) {
        String username = userRepository.getUsernameById(userId);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String username) {
        UserResponseDto userResponseDto = userService.getUserInfo(username);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody Users user) {
        UserResponseDto userResponseDto = userService.register(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDto userDto) {
        AuthenticationResponse authenticationResponse = userService.login(userDto);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PatchMapping("/email")
    public ResponseEntity<Void> changeUserEmail(@RequestBody UpdateRequestDto updateRequestDto) {
        userService.changeEmail(updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/username")
    public ResponseEntity<Void> changeUsername(@RequestBody UpdateRequestDto updateRequestDto) {
        userService.changeUsername(updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody UpdateRequestDto updateRequestDto) {
        userService.changePassword(updateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/non-sensitive")
    public ResponseEntity<Void> updateNonSensitive(@RequestBody UserResponseDto userResponseDto) {
        userService.makeChangesToProfile(userResponseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
