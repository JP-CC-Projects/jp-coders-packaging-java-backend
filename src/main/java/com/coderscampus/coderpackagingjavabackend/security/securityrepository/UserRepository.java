package com.coderscampus.coderpackagingjavabackend.security.securityrepository;

import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}