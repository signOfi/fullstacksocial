package com.social.tweetbackend.security.service;

import com.social.tweetbackend.exception.InvalidCredentialsExceptionJwt;
import com.social.tweetbackend.exception.ResourceNotFoundException;
import com.social.tweetbackend.exception.UserAlreadyExistsExceptionJwt;
import com.social.tweetbackend.security.model.*;
import com.social.tweetbackend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.social.tweetbackend.security.model.UserResponseDto;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseDtoMapper userResponseDtoMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService,
                       UserDtoMapper userDtoMapper,
                       UserResponseDtoMapper userResponseDtoMapper)
     {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userResponseDtoMapper = userResponseDtoMapper;
    }

    public String getUserNameById(Long id) {
        String username = userRepository.getUsernameById(id);
        if (username == null)
            throw new ResourceNotFoundException("user id", "id", id);
        return username;
    }

    public UserResponseDto register(Users user) {
        /* Check if username already exists */
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsExceptionJwt("Username already exists");
        }

        /* Set default role as USER */
        user.getRoles().add(UserRole.ROLE_USER);

        /* Want to crypt the password */
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return userResponseDtoMapper.apply(user);
    }

    public AuthenticationResponse login(UserDto userDto) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.username(),
                        userDto.password()));
        if (authentication.isAuthenticated()) {

            Users authenticatedUser = userRepository.findByUsername(userDto.username())
                    .orElseThrow( () -> new ResourceNotFoundException("Username not found", "username",
                            userDto.username()));

            String token = jwtService.generateToken(authenticatedUser.getUsername());
            UserResponseDto userResponseDto = userResponseDtoMapper.apply(authenticatedUser);
            return new AuthenticationResponse(token, userResponseDto, authenticatedUser.getId());
        }

        /* Reach here authentication failed somehow */
        throw new InvalidCredentialsExceptionJwt("Invalid credentials with token or username/password");
    }

    /* Makes a user an admin */
    public void addAdminRole(String username) {
        Users admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username Not Found", "username", username));
        admin.getRoles().add(UserRole.ROLE_ADMIN);
        userRepository.save(admin);
    }

    /* Should be able to make changes to the User */
    public UserResponseDto makeChangesToProfile(UserResponseDto userResponseDto) {

        Users user = getUsersObject(userResponseDto.username());

        /* Update all fields that contain no sensitive data */
        user.setFirstName(userResponseDto.firstName());
        user.setLastName(userResponseDto.lastName());
        user.setPhoneNumber(userResponseDto.phoneNumber());
        userRepository.save(user);

        return userResponseDto;
    }

    public void changeEmail(UpdateRequestDto updateRequestDto) {

        Users user = getUsersObject(updateRequestDto.userResponseDto().username());

        String updatedEmail = updateRequestDto.sensitiveInformation();

        /* Check if new email already exists */
        if (userRepository.existsByEmail(updatedEmail)) {
            throw new InvalidCredentialsExceptionJwt("Email already exists");
        }

        user.setEmail(updatedEmail);
        userRepository.save(user);
    }

    public void changeUsername(UpdateRequestDto updateRequestDto) {

        Users user = getUsersObject(updateRequestDto.userResponseDto().username());

        String updatedUsername = updateRequestDto.sensitiveInformation();

        if (userRepository.existsByEmail(updatedUsername))
            throw new InvalidCredentialsExceptionJwt("Email already exists");

        user.setEmail(updatedUsername);
        userRepository.save(user);
    }



    public void changePassword (UpdateRequestDto updateRequestDto) {

        Users user = getUsersObject(updateRequestDto.userResponseDto().username());

        String updatedPassword = updateRequestDto.sensitiveInformation();

        /* Validate the password */
        if (updatedPassword.length() < 8)
            throw new InvalidCredentialsExceptionJwt("Password does not meet the requirement");

        user.setPassword(bCryptPasswordEncoder.encode(updatedPassword));
        userRepository.save(user);
    }

    public UserResponseDto getUserInfo(String username) {
        Users users =  getUsersObject(username);
        return userResponseDtoMapper.apply(users);
    }


    public Users getUsersObject(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow( () -> new ResourceNotFoundException("User" , "username", username));
    }

}
