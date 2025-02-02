package com.example.crud.service.impl;

import com.example.crud.dto.UserDto;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String USER_CACHE_PREFIX = "user:";

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Find or create the role based on userDto's role
        Role role = roleRepository.findByName(userDto.getRole());
        if (role == null) {
            role = new Role();
            role.setName(userDto.getRole());
            roleRepository.save(role);
        }
        user.setRoles(Set.of(role));//转化为set以支持用户类别
        userRepository.save(user);

        // Cache the user
        String key = USER_CACHE_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoles().toString());
        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(id);
        String key = USER_CACHE_PREFIX + id;
        redisTemplate.delete(key);
    }
    @Override
    public UserDto findUserById(Long id) {
        String key = USER_CACHE_PREFIX + id;
        User user = (User) redisTemplate.opsForValue().get(key);
        if (user == null) {
            user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            redisTemplate.opsForValue().set(key, user);
        }
        return mapToUserDto(user);
    }
    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userDto.getId()));
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        String key = USER_CACHE_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, user);
    }
    @Override
    public Page<UserDto> findPaginated(Pageable pageable) {//真正利用上了redis，redis最重要的就是每次在users界面不用反复调动MySQL
        String cacheKey = USER_CACHE_PREFIX + pageable.getPageNumber() + ":" + pageable.getPageSize();
        List<User> users = (List<User>) redisTemplate.opsForValue().get(cacheKey);
        Page<User> usersPage;

        if (users == null) {
            // 如果缓存中没有数据，从数据库中查询
            usersPage = userRepository.findAll(pageable);
            // 将查询结果的内容缓存到Redis
            redisTemplate.opsForValue().set(cacheKey, usersPage.getContent());
        } else {
            // 从Redis缓存中构建分页结果
            long total = userRepository.count();  // 获取总记录数
            usersPage = new PageImpl<>(users, pageable, total);
        }
        return usersPage.map(this::mapToUserDto);
    }
    @Override
    public Page<UserDto> findUsersByEmailContaining(String email, Pageable pageable) {//正常搜索和模糊搜索都要redis，先搜索redis，如果没有就搜MySQL
        String cacheKey = USER_CACHE_PREFIX + email + ":" + pageable.getPageNumber() + ":" + pageable.getPageSize();
        List<User> users = (List<User>) redisTemplate.opsForValue().get(cacheKey);
        Page<User> usersPage;

        if (users == null) {
            // 如果缓存中没有数据，从数据库中查询
            usersPage = userRepository.findByEmailContaining(email, pageable);
            // 将查询结果的内容缓存到Redis
            redisTemplate.opsForValue().set(cacheKey, usersPage.getContent());
        } else {
            // 从Redis缓存中构建分页结果
            long total = userRepository.count();  // 获取总记录数
            usersPage = new PageImpl<>(users, pageable, total);
        }

        return usersPage.map(this::mapToUserDto);
    }
}