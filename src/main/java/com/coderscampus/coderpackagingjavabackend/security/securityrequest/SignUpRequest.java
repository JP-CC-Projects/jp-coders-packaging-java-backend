package com.coderscampus.coderpackagingjavabackend.security.securityrequest;

import java.util.Optional;

public record SignUpRequest(String email,
                            String password,
                            String firstName,
                            String lastName,
                            Optional<String> authorityOpt) {
}
