package com.jm.controller;

import com.jm.dto.LoginRequest;
import com.jm.dto.LoginResponse;
import com.jm.model.Cliente;
import com.jm.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        return clienteRepository.findByCorreo(request.getCorreo())
                .filter(cliente -> passwordEncoder.matches(
                        request.getPassword(),
                        cliente.getPassword()))
                .<ResponseEntity<?>>map(cliente ->
                        ResponseEntity.ok(new LoginResponse(
                                cliente.getId(),
                                cliente.getNombre(),
                                cliente.getCorreo())))
                .orElseGet(() ->
                        ResponseEntity.status(401).build());
    }
}