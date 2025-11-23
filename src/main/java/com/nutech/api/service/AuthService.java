package com.nutech.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutech.api.dto.request.LoginRequest;
import com.nutech.api.dto.request.RegistrationRequest;
import com.nutech.api.dto.response.LoginResponse;
import com.nutech.api.exception.InvalidCredentialsException;
import com.nutech.api.exception.UserAlreadyExistsException;
import com.nutech.api.model.User;
import com.nutech.api.repository.BalanceRepository;
import com.nutech.api.repository.UserRepository;
import com.nutech.api.security.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BalanceRepository balanceRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void register(RegistrationRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new UserAlreadyExistsException(
                "Email sudah terdaftar, silahkan gunakan email lain"
            );
        }

        String hashedPassword = passwordEncoder.encode(req.getPassword());
        User user = new User();

        user.setEmail(req.getEmail());
        user.setPassword(hashedPassword);
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());

        userRepository.save(user);
        balanceRepository.initializeBalance(user.getId());
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
            () -> new InvalidCredentialsException("Username atau password salah")
        );

        boolean passwordMatches = passwordEncoder.matches(req.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Username atau password salah");
        }

        return new LoginResponse(jwtUtil.generateToken(user.getEmail(), user.getId())); 
    }
    
}
