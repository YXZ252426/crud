package com.example.crud.repository;

import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper


public interface UserRepository
{
    List<Role> findRolesByUserId(Long userId);
    long countUsers();
    void updateUser(User user);
    void deleteById(Long id);
    User findByEmail(String email);
    User findByID(Long id);
    List<User> findAllUsers();
    Page<User> findByEmailContaining(String email, Pageable pageable);//
    void insertUser(User user);

}
/*
Page<User>: 返回一个 Page 对象，包含了用户实体 User 的分页结果。
findByEmailContaining: 查询方法名。
find: 表示这是一个查询操作。
By: 表示查询条件的开始。
Email: 表示查询条件基于 User 实体类中的 email 属性。
Containing: 表示进行模糊查询。
String email: 查询条件参数，表示电子邮件包含指定关键字。
Pageable pageable: 分页参数，包含页码、每页记录数、排序信息。
Spring Data JPA会将上述方法解析为类似的SQL查询：
 */