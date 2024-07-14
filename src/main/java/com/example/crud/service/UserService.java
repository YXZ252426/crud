package com.example.crud.service;

import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    
}