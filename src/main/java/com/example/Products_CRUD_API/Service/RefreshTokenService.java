package com.example.Products_CRUD_API.Service;

import com.example.Products_CRUD_API.entity.RefreshToken;
import com.example.Products_CRUD_API.entity.User;
import com.example.Products_CRUD_API.exception.TokenExpiredException;
import com.example.Products_CRUD_API.repository.RefreshTokenRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final AsyncAuditService asyncAuditService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDurationMs;

    public RefreshTokenService(
            AsyncAuditService asyncAuditService,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.asyncAuditService = asyncAuditService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // CREATE + ROTATE REFRESH TOKEN
    @Transactional
    public RefreshToken createRefreshToken(User user) {

        // delete old token (rotation)
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        RefreshToken savedToken =
                refreshTokenRepository.save(refreshToken);

        // async audit log
        asyncAuditService.logUserAction(
                "Refresh token created for user: " + user.getUsername()
        );

        return savedToken;
    }

    // FIND TOKEN
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));
    }

    // VERIFY EXPIRATION
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("Refresh token expired");
        }
    }

    // DELETE TOKEN (used in rotation)
    public void deleteToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }
}