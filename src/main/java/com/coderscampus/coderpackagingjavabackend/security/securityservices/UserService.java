package com.coderscampus.coderpackagingjavabackend.security.securityservices;

import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<User> findAll();
}