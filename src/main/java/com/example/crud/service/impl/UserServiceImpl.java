package com.example.crud.service.impl;


import com.example.crud.dto.UserDto;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.entity.UserRole;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.repository.UserRoleRepository;
import com.example.crud.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    };
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.insertUser(user);

        // Find or create the role based on userDto's role
        for (String roleName : userDto.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                roleRepository.insertRole(role);
            }

        // Create and save user_roles relationship
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleRepository.InsertUserRole(userRole);
        }
    }
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setId(user.getId());
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        //设置身份！！
        userDto.setEmail(user.getEmail());

        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        userDto.setRoles(roleNames);
        return userDto;
    }
    @Override
    public void deleteUserById(Long id) {
        User user=userRepository.findByID(id);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user Id: " + id);
        }
        userRoleRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }
    public UserDto findUserById(Long id) {
        User user=userRepository.findByID(id);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user Id: " + id);
        }
        return mapToUserDto(user);
    }
    @Override
    public User findUserByEmail(String email){
        User user=userRepository.findByEmail(email);
        return user;
    }
    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByID(userDto.getId());
        if (user == null) {
            throw new IllegalArgumentException("Invalid user Id: " + userDto.getId());
        }
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        userRepository.updateUser(user);
    }//是不是可以搞一个，只有管理员可以修改用户权限
    //考虑以下场景：在更新用户信息的同时，还需要更新用户的角色。如果更新用户角色失败，而用户信息已经更新，这会导致数据不一致。通过使用 @Transactional 注解，你可以确保这两个操作要么都成功，要么都失败。
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserDto> findPaginated(int page, int size) {
        size=3;
        PageHelper.startPage(page, size);
        List<User> users = userRepository.findAllUsers();
        return new PageInfo<>(users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList()));
    }

    //具体来说，stream() 方法将 List<User> 转换成流（stream），map(this::mapToUserDto) 将流中的每个 User 对象转换为 UserDto 对象，最后 collect(Collectors.toList()) 将转换后的 UserDto 对象收集成一个 List<UserDto>。
    /*
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    @Override
    public PageInfo<UserDto> findPaginated(Pageable pageable) {//真正利用上了redis，redis最重要的就是每次在users界面不用反复调动MySQL
        List<User> users = (List<User>) redisTemplate.opsForValue().get(cacheKey);
        Page<User> usersPage;
        long total = userRepository.countUsers();  // 获取总记录数
        return usersPage.map(this::mapToUserDto);
    }
    @Override
    public Page<UserDto> findUsersByEmailContaining(String email, Pageable pageable) {//正常搜索和模糊搜索都要redis，先搜索redis，如果没有就搜MySQL
        String cacheKey = USER_CACHE_PREFIX + email + ":" + pageable.getPageNumber() + ":" + pageable.getPageSize();
        List<User> users = (List<User>) redisTemplate.opsForValue().get(cacheKey);
        Page<User> usersPage;
        long total = userRepository.countUsers();  // 获取总记录数usersPage = new PageImpl<>(users, pageable, total);
        }

        return usersPage.map(this::mapToUserDto);
    }*/
}