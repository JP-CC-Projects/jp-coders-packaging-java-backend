package com.coderscampus.coderpackagingjavabackend.security.securityservices;

import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignInRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.SignUpRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityrequest.UpdateProfileRequest;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.JwtAuthenticationResponse;
import com.coderscampus.coderpackagingjavabackend.security.securityresponse.JwtUserUpdateResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
    void verifyUserBeforeUpdate(UpdateProfileRequest request);
    JwtUserUpdateResponse issueNewTokens(String userEmail);
}