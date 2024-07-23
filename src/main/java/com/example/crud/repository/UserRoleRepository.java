package com.example.crud.repository;

import com.example.crud.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleRepository {
    void InsertUserRole(UserRole userRole);
    void deleteByUserId(Long userId);
}
