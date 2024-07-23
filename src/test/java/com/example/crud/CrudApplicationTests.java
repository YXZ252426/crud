package com.example.crud;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
public class
CrudApplicationTests {

	@Autowired
	private UserRepository userRepository;


	@Test
	void testGetAll() {


		userRepository.deleteById(1L);

	}}
