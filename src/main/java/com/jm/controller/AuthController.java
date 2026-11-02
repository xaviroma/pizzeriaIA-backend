package com.jm.controller;

import com.jm.config.JwtService;
import com.jm.dto.LoginRequest;
import com.jm.dto.LoginResponse;
import com.jm.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        return clienteRepository.findByCorreo(request.getCorreo())
                .filter(cliente -> passwordEncoder.matches(
                        request.getPassword(),
                        cliente.getPassword()))
                .<ResponseEntity<?>>map(cliente -> {
                    String token = jwtService.generateToken(
                            cliente.getCorreo(),
                            cliente.getRol()
                    );

                    return ResponseEntity.ok(new LoginResponse(
                            cliente.getId(),
                            cliente.getNombre(),
                            cliente.getCorreo(),
                            token,
                            cliente.getRol()));
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}