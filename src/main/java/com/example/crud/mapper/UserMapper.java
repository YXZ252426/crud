package com.example.crud.mapper;

import com.example.crud.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectUser(Long id);

    @Select("SELECT * FROM users WHERE name = #{name}")
    List<User> selectUsersByName(String name);

    @Insert("INSERT INTO users(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Update("UPDATE users SET name = #{name} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(Long id);
}