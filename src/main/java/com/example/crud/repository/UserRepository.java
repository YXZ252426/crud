package com.example.crud.repository;

import com.example.crud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);//findAll涉及到对数据库的操作，所以要在这里写
}