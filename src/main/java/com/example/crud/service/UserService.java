package com.example.crud.service;

import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void deleteUserById(Long id);
    User findUserByEmail(String email);
    void updateUser(UserDto userDto);
    PageInfo<UserDto> findPaginated(int page, int size);
    UserDto findUserById(Long id);
    /*User findUserByEmail(String email);
    Page<UserDto> findPaginated(Pageable pageable);//这个功能最终都是要用UserRepository
    Page<UserDto> findUsersByEmailContaining(String email, Pageable pageable);*/
}