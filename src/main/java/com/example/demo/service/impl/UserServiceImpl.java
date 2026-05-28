package com.example.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByMobileNumber(userRequestDto.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        User user = modelMapper.map(userRequestDto, User.class);

        User saveUser = userRepository.save(user);

        return modelMapper.map(saveUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));
        
        return modelMapper.map(user, UserResponseDto.class);
    }

}
