package com.coderscampus.coderpackagingjavabackend.security.securityservices;


import com.coderscampus.coderpackagingjavabackend.security.securitydomain.Authority;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.Role;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import com.coderscampus.coderpackagingjavabackend.security.securityrepository.UserRepository;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignInRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignUpRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.UpdateProfileRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.JwtAuthenticationResponse;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.JwtUserUpdateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                     JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = new User()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authority(Role.USER.name()).build();
        request.authorityOpt().ifPresent(auth -> user.getAuthorities().add(new Authority(auth, user)));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());

        String encodedPassword = passwordEncoder.encode(request.password());
        return new JwtAuthenticationResponse(jwt, refreshToken.getToken());
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshTokenOpt = refreshTokenService.findByToken(jwt);

        if (refreshTokenOpt.isPresent()) {
            return new JwtAuthenticationResponse(jwt, refreshTokenOpt.get().getToken());
        } else {
            return new JwtAuthenticationResponse(jwt, refreshTokenService.createRefreshToken(user.getId()).getToken());
        }
    }

    public void verifyUserBeforeUpdate(UpdateProfileRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String loggedInUserEmail = ((UserDetails)principal).getUsername();
            if (!loggedInUserEmail.equals(request.email())) {
                throw new IllegalArgumentException("Logged in user does not match the requested update user");
            }
            User user = userRepository.findByEmail(loggedInUserEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid current password");
            }
        } else {
            throw new IllegalStateException("User authentication details not found");
        }
    }
    @Override
    public JwtUserUpdateResponse issueNewTokens(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newJwtToken = jwtService.generateToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return new JwtUserUpdateResponse(newJwtToken, newRefreshToken);
    }
}

