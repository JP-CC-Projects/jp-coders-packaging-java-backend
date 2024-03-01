package com.coderscampus.coderpackagingjavabackend.security.securitycontroller;

import com.coderscampus.coderpackagingjavabackend.security.securitydomain.Authority;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import com.coderscampus.coderpackagingjavabackend.security.securityrepository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AdminController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Value("${admin.firstName}")
    private String adminFirstName;
    @Value("${admin.lastName}")
    private String adminLastName;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminUPassword;

    @PostConstruct // This annotation is used to create an admin user during application startup
    public void init() {
        createAdminUser();
    }

    public void createAdminUser() {
        User adminUser = new User();
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }
        adminUser.setFirstName(adminFirstName);
        adminUser.setLastName(adminLastName);
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(passwordEncoder.encode(adminUPassword));
        Authority adminAuth = new Authority("ROLE_ADMIN", adminUser);
        Authority userAuth = new Authority("ROLE_USER", adminUser);
        adminUser.setAuthorities(Arrays.asList(adminAuth, userAuth));
        userRepository.save(adminUser);
    }
}
