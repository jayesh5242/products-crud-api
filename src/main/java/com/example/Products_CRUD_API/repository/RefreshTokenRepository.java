package com.example.Products_CRUD_API.repository;

import com.example.Products_CRUD_API.entity.RefreshToken;
import com.example.Products_CRUD_API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
