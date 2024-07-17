package com.example.crud.repository;

import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleRepository
{
    @Insert("INSERT INTO users(id,name) VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(Role role);

    @Select("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Role> findRolesByUserId(Long userId);

    @Select("SELECT * FROM roles WHERE name = #{name}")
    Role findByName(String name);
}
