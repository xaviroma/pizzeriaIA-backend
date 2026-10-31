package com.jm.controller;

import com.jm.dto.ClienteResponse;
import com.jm.model.Cliente;
import com.jm.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteController(
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<ClienteResponse> obtenerClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerClientePorId(
            @PathVariable Integer id,
            Authentication authentication) {

        return clienteRepository.findById(id)
                .map(cliente -> {
                    boolean esAdmin = authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (!esAdmin && !cliente.getCorreo().equals(authentication.getName())) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .<ClienteResponse>build();
                    }

                    return ResponseEntity.ok(convertirAResponse(cliente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClienteResponse crearCliente(@RequestBody Cliente cliente) {
        cliente.setRol("CLIENTE");
        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        }

        return convertirAResponse(clienteRepository.save(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarCliente(
            @PathVariable Integer id,
            @RequestBody Cliente detallesCliente,
            Authentication authentication) {

        return clienteRepository.findById(id).map(cliente -> {

            boolean esAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (!esAdmin && !cliente.getCorreo().equals(authentication.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .<ClienteResponse>build();
            }

            cliente.setNombre(detallesCliente.getNombre());
            cliente.setCorreo(detallesCliente.getCorreo());
            cliente.setTelefono(detallesCliente.getTelefono());
            cliente.setDireccion(detallesCliente.getDireccion());

            if (detallesCliente.getPassword() != null
                    && !detallesCliente.getPassword().isBlank()) {
                cliente.setPassword(passwordEncoder.encode(detallesCliente.getPassword()));
            }

            return ResponseEntity.ok(
                    convertirAResponse(clienteRepository.save(cliente)));

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private ClienteResponse convertirAResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getDireccion()
        );
    }
}