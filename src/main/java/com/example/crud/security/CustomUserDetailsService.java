package com.example.crud.security;
/*
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));//转化使security可以识别
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }//理解他是如何实现密码验证的，他靠的是 org. springframework. security. core，转化成他能识别的格式
}*/