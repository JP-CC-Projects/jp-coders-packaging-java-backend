package com.coderscampus.coderpackagingjavabackend.security.securitycontroller;

import com.coderscampus.coderpackagingjavabackend.security.securityrepository.UserRepository;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignUpRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityservices.AuthenticationServiceImpl;
import com.coderscampus.coderpackagingjavabackend.security.securityservices.JwtServiceImpl;
import com.coderscampus.coderpackagingjavabackend.security.securityservices.RefreshTokenService;
import com.coderscampus.coderpackagingjavabackend.security.securityservices.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationController {

    private final UserServiceImpl userService;
    private final AuthenticationServiceImpl authenticationService;
    private final JwtServiceImpl jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserRepository userRepository;


    public RegistrationController(UserServiceImpl userService, AuthenticationServiceImpl authenticationService,
                                  JwtServiceImpl jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder,
                                  UserRepository userRepository) {
        super();
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        if(userRepository.findByEmail(request.email()).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: User with email " + request.email() + " already exists.");
        };
        return ResponseEntity.ok(authenticationService.signup(request));
    }
}

