package finance_backend.service;

import finance_backend.dto.*;
import finance_backend.entity.User;
import finance_backend.repository.UserRepository;
import finance_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already registered");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name());
        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name());
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive())
            throw new RuntimeException("Account is deactivated");

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name());
        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name());
    }
}