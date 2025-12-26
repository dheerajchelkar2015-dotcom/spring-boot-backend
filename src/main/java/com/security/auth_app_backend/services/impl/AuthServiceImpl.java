package com.security.auth_app_backend.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.auth_app_backend.dtos.UserDto;
import com.security.auth_app_backend.services.AuthService;
import com.security.auth_app_backend.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserDto userDto1 = userService.createUser(userDto);
        return userDto1;
    }
    
}
