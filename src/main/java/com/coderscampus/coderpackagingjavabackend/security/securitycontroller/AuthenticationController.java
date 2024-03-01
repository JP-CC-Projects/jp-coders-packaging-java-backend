package com.coderscampus.coderpackagingjavabackend.security.securitycontroller;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.RefreshToken;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignInRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.JwtAuthenticationResponse;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.TokenRefreshResponse;
import com.coderscampus.coderpackagingjavabackend.security.securityservices.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService,
                                    RefreshTokenService refreshTokenService,
                                    JwtServiceImpl jwtService,
                                    UserServiceImpl userService) {
        super();
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,
                                          HttpServletResponse response) {
        String requestRefreshToken = refreshTokenService.extractRefreshTokenFromCookies(request.getCookies());
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", requestRefreshToken)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .sameSite("Lax")
                            .build();
                    response.addHeader("Set-Cookie", refreshCookie.toString());
                    return ResponseEntity.ok(new TokenRefreshResponse(token));
                })
                .orElseThrow(() -> new IllegalStateException(
                        "Refresh token " + requestRefreshToken + " is not in database!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateLogin(@RequestBody SignInRequest request, HttpServletResponse response) {
        Optional<User> existingUser = userService.findUserByEmail(request.email());
        if (existingUser.isPresent()) {
            JwtAuthenticationResponse jwtAuthResponse = authenticationService.signin(request);
            String accessToken = jwtAuthResponse.token();
            String refreshToken = jwtAuthResponse.refreshToken();
            // Send refresh token as HTTP-only cookie
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Lax")
                    .build();
            response.addHeader("Set-Cookie", refreshCookie.toString());
            return ResponseEntity.ok(new TokenRefreshResponse(accessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}
