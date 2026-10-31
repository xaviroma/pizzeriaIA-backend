package com.jm.controller;

import com.jm.model.Pedido;
import com.jm.model.PedidoRequest;
import com.jm.repository.PedidoRepository;
import com.jm.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    public PedidoController(PedidoRepository pedidoRepository,
                            PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }
    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> obtenerPedidosPorCliente(@PathVariable Integer clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @PostMapping
    public Integer crearPedido(@RequestBody PedidoRequest request) {
        Pedido pedido = pedidoService.crearPedido(request);
        return pedido.getId();
    }
}
