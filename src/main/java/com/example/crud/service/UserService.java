package com.example.crud.service;

import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    void deleteUserById(Long id);
    void updateUser(UserDto userDto);
    UserDto findUserById(Long id);
    Page<UserDto> findPaginated(Pageable pageable);//这个功能最终都是要用UserRepository
    Page<UserDto> findUsersByEmailContaining(String email, Pageable pageable);
}