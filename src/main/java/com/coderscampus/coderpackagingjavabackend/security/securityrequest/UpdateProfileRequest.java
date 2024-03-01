package com.coderscampus.coderpackagingjavabackend.security.securityrequest;

public record UpdateProfileRequest(String email,
                                   String firstName,
                                   String lastName,
                                   String currentPassword,
                                   String newPassword) {
}
