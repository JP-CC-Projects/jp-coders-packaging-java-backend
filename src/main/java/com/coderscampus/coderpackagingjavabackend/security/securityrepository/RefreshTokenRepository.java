package com.coderscampus.coderpackagingjavabackend.security.securityrepository;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.RefreshToken;
import com.coderscampus.coderpackagingjavabackend.security.securitydomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}