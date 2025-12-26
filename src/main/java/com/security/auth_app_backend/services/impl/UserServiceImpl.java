package com.security.auth_app_backend.services.impl;

import java.time.Instant;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.security.auth_app_backend.dtos.UserDto;
import com.security.auth_app_backend.entities.Provider;
import com.security.auth_app_backend.entities.User;
import com.security.auth_app_backend.exceptions.ResourceNotFoundException;
import com.security.auth_app_backend.helpers.UserHelper;
import com.security.auth_app_backend.repositories.UserRepository;
import com.security.auth_app_backend.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        
        if(userDto.getEmail()==null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is Required");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with Given Email Already Exist");
        }

        User user = modelMapper.map(userDto, User.class);

        user.setProvider(userDto.getProvider()!=null ? userDto.getProvider():Provider.LOCAL);
        //role assign for authorization
        User savedUser = userRepository.save(user);


        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    @GetMapping
    public UserDto getUserByEmail(String email) {
        
        User user = userRepository
        .findByEmail(email)
        .orElseThrow(()->new ResourceNotFoundException("User Not Found with given email id"));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uId = UserHelper.parseUuid(userId);
        User exisitingUser = userRepository.findById(uId).orElseThrow(()->new ResourceNotFoundException("User Not found with Given Id"));

        if (userDto.getName()!=null) {
            exisitingUser.setName(userDto.getName());
        }
        if (userDto.getImage()!=null) {
            exisitingUser.setImage(userDto.getImage());
        }
        if (userDto.getProvider()!=null) {
            exisitingUser.setProvider(userDto.getProvider());
        }
        if (userDto.getPassword()!=null) {
            exisitingUser.setPassword(userDto.getPassword());
        }
        
        exisitingUser.setUpdatedAt(Instant.now());
        exisitingUser.setEnable(userDto.isEnable());

        User updatedUser = userRepository.save(exisitingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        UUID uId = UserHelper.parseUuid(userId);
        User user = userRepository.findById(uId).orElseThrow(()-> new ResourceNotFoundException("User Not found with Given Id"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(UserHelper.parseUuid(userId)).orElseThrow(()-> new ResourceNotFoundException("User Not found with Given Id"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepository
        .findAll()
        .stream()
        .map(user -> modelMapper.map(user, UserDto.class))
        .toList();
    }

}
