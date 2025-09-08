package org.example.longdistancebusbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.JwtUtil;
import org.example.longdistancebusbackend.dto.TokenResponseDTO;
import org.example.longdistancebusbackend.dto.UserDTO;
import org.example.longdistancebusbackend.service.UserService;
import org.example.longdistancebusbackend.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.example.longdistancebusbackend.dto.LoginRequestDTO;
import org.springframework.http.HttpStatus;
import org.example.longdistancebusbackend.dto.LoginResponseDTO;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                   HttpServletRequest servletRequest) {
        try {
            UserDTO user = userService.authenticate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

            String token = jwtUtil.generateToken(user.getEmail());

            // Login notification
            String ip = servletRequest.getRemoteAddr();
            emailService.sendLoginNotification(user.getEmail(), ip);

            TokenResponseDTO response = new TokenResponseDTO(token, user);
            return ResponseEntity.ok(new APIResponse(200, "Login successful", response));

        } catch (RuntimeException ex) {
            log.warn("Login failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse(401, ex.getMessage(), null));
        } catch (Exception ex) {
            log.error("Login error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, "Internal server error", null));
        }
    }
}
