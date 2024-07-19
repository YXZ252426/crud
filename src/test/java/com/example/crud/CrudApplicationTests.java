package com.example.crud;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class CrudApplicationTests {

	@Autowired
	private UserRepository userRepository;


	@Test
	void testGetAll() {
		User user= userRepository.findByEmail("23051035@hdu.edu.cn");
		System.out.println(user);
	}
}
