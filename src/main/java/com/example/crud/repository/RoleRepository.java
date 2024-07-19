package com.example.crud.repository;

import com.example.crud.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleRepository
{
    void insertRole(Role role);

    List<Role> findRolesByUserId(Long userId);

    Role findByName(String name);
}
