package com.jm.controller;

import com.jm.model.PedidoProducto;
import com.jm.repository.PedidoProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido-producto")
public class PedidoProductoController {

    private final PedidoProductoRepository pedidoProductoRepository;

    public PedidoProductoController(PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoProductoRepository = pedidoProductoRepository;
    }

    // Obtener todas las relaciones Pedido-Producto
    @GetMapping
    public List<PedidoProducto> obtenerTodasLasRelaciones() {
        return pedidoProductoRepository.findAll();
    }

    // Obtener una relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoProducto> obtenerRelacionPorId(@PathVariable Integer id) {
        Optional<PedidoProducto> relacion = pedidoProductoRepository.findById(id);
        return relacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva relación entre pedido y producto
    @PostMapping
    public PedidoProducto crearRelacion(@RequestBody PedidoProducto pedidoProducto) {
        return pedidoProductoRepository.save(pedidoProducto);
    }

    // Eliminar una relación entre pedido y producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable Integer id) {
        if (pedidoProductoRepository.existsById(id)) {
            pedidoProductoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
