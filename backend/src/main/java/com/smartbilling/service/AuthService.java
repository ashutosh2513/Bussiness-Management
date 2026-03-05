package com.smartbilling.service;

import com.smartbilling.domain.User;
import com.smartbilling.dto.AuthDtos;
import com.smartbilling.dto.RbacDtos;
import com.smartbilling.repository.UserRepository;
import com.smartbilling.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PrivilegeService privilegeService;

    @Transactional
    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        userRepository.findByEmailIgnoreCase(request.email()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already registered");
        });

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setActive(true);
        userRepository.save(user);

        return buildAuthResponse(user);
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isActive() || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return buildAuthResponse(user);
    }

    private AuthDtos.AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(
                user.getEmail(),
                Map.of(
                        "uid", user.getId().toString(),
                        "role", user.getRole().name()
                )
        );
        java.util.List<RbacDtos.PrivilegeModuleDto> privileges = privilegeService.getPrivilegesForRoleCode(user.getRole().name());
        return new AuthDtos.AuthResponse(token, user.getFullName(), user.getEmail(), user.getRole(), privileges);
    }
}
