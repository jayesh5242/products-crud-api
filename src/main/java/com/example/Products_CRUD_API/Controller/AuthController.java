package com.example.Products_CRUD_API.Controller;

import com.example.Products_CRUD_API.Service.RefreshTokenService;
import com.example.Products_CRUD_API.Service.UserService;
import com.example.Products_CRUD_API.dto.LoginRequest;
import com.example.Products_CRUD_API.dto.RefreshTokenRequest;
import com.example.Products_CRUD_API.entity.RefreshToken;
import com.example.Products_CRUD_API.entity.User;
import com.example.Products_CRUD_API.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserService userService,
            RefreshTokenService refreshTokenService,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {

        userService.register(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userService.validateUser(
                request.getUsername(),
                request.getPassword()
        );

        String accessToken = jwtUtil.generateToken(user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken.getToken()
                )
        );
    }

    // ---------------- REFRESH TOKEN ----------------
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        RefreshToken oldRefreshToken =
                refreshTokenService.findByToken(request.getRefreshToken());

        refreshTokenService.verifyExpiration(oldRefreshToken);

        User user = oldRefreshToken.getUser();

        //  ROTATION: delete old & create new
        refreshTokenService.deleteToken(oldRefreshToken);

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        String newAccessToken =
                jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(
                Map.of(
                        "accessToken", newAccessToken,
                        "refreshToken", newRefreshToken.getToken()
                )
        );
    }
}