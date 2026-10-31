package com.jm.controller;

import com.jm.model.Pedido;
import com.jm.model.PedidoRequest;
import com.jm.repository.PedidoRepository;
import com.jm.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.jm.model.Cliente;
import com.jm.repository.ClienteRepository;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;
    private final ClienteRepository clienteRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            PedidoService pedidoService,
                            ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
        this.clienteRepository = clienteRepository;
    }
    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> obtenerPedidosPorCliente(
            @PathVariable Integer clienteId,
            Authentication authentication) {

        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (esAdmin) {
            return pedidoRepository.findByClienteId(clienteId);
        }

        return pedidoRepository.findByCliente_IdAndCliente_Correo(
                clienteId,
                authentication.getName()
        );
    }

    @PostMapping
    public Integer crearPedido(
            @RequestBody PedidoRequest request,
            Authentication authentication) {

        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            Cliente cliente = clienteRepository.findByCorreo(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            request.setClienteId(cliente.getId());
        }

        Pedido pedido = pedidoService.crearPedido(request);
        return pedido.getId();
    }
}
