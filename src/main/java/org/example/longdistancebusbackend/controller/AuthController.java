package org.example.longdistancebusbackend.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.JwtUtil;
import org.example.longdistancebusbackend.dto.*;
import org.example.longdistancebusbackend.service.AdminService;
import org.example.longdistancebusbackend.service.UserService;
import org.example.longdistancebusbackend.service.impl.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.example.longdistancebusbackend.Util.APIResponse;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                   HttpServletRequest servletRequest) {
        try {
            String ip = servletRequest.getRemoteAddr();

            // Try user authentication
            try {
                UserDTO user = userService.authenticate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
                String token = jwtUtil.generateToken(user.getEmail());
                emailService.sendLoginNotification(user.getEmail(), ip);

                return ResponseEntity.ok(
                        new APIResponse(200, "User login successful", new TokenResponseDTO(token, user))
                );
            } catch (RuntimeException userEx) {
                // Try admin authentication
                try {
                    AdminDTO admin = adminService.authenticateAdmin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
                    String token = jwtUtil.generateToken(admin.getEmail());
                    emailService.sendLoginNotification(admin.getEmail(), ip);

                    return ResponseEntity.ok(
                            new APIResponse(200, "Admin login successful", new TokenResponseDTO(token, admin))
                    );
                } catch (RuntimeException adminEx) {
                    // Neither user nor admin
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new APIResponse(401, "Invalid credentials", null));
                }
            }

        } catch (Exception ex) {
            log.error("Login error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, "Internal server error", null));
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload, HttpServletRequest servletRequest) {
        String idToken = payload.get("idToken");

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                    .Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("948863779289-371gubqgtieojjvm7onlqm6qc8gi8i2r.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse(401, "Invalid ID token", null));
            }

            GoogleIdToken.Payload tokenPayload = googleIdToken.getPayload();
            String email = tokenPayload.getEmail();

            // Check if user exists or create one
            UserDTO user = userService.findOrCreateGoogleUser(email);

            // Issue JWT
            String jwt = jwtUtil.generateToken(email);

            String ip = servletRequest.getRemoteAddr();
            emailService.sendLoginNotification(email, ip);

            return ResponseEntity.ok(new APIResponse(200, "Google login success", new TokenResponseDTO(jwt, user)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(500, "Server error", null));
        }
    }

//    @PostMapping("/admin-login")
//    public ResponseEntity<?> adminLogin(@RequestBody LoginRequestDTO loginRequestDTO,
//                                        HttpServletRequest servletRequest) {
//        try {
//            AdminDTO admin = adminService.authenticateAdmin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
//
//            String token = jwtUtil.generateToken(admin.getEmail());
//
//            // Login notification
//            String ip = servletRequest.getRemoteAddr();
//            emailService.sendLoginNotification(admin.getEmail(), ip);
//
//            TokenResponseDTO response = new TokenResponseDTO(token, admin);
//            return ResponseEntity.ok(new APIResponse(200, "Admin login successful", response));
//
//        } catch (RuntimeException ex) {
//            log.warn("Admin login failed: {}", ex.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new APIResponse(401, ex.getMessage(), null));
//        } catch (Exception ex) {
//            log.error("Admin login error: {}", ex.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new APIResponse(500, "Internal server error", null));
//        }
//    }
}
