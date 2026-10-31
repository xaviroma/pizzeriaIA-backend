package com.jm.controller;

import com.jm.model.ProductoIngrediente;
import com.jm.repository.ProductoIngredienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto-ingrediente")
public class ProductoIngredienteController {

    private final ProductoIngredienteRepository productoIngredienteRepository;

    public ProductoIngredienteController(ProductoIngredienteRepository productoIngredienteRepository) {
        this.productoIngredienteRepository = productoIngredienteRepository;
    }

    // Obtener todas las relaciones Producto-Insumo
    @GetMapping
    public List<ProductoIngrediente> obtenerTodasLasRelaciones() {
        return productoIngredienteRepository.findAll();
    }

    // Obtener relación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoIngrediente> obtenerRelacionPorId(@PathVariable Integer id) {
        Optional<ProductoIngrediente> relacion = productoIngredienteRepository.findById(id);
        return relacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva relación entre producto e ingrediente
    @PostMapping
    public ProductoIngrediente crearRelacion(@RequestBody ProductoIngrediente productoIngrediente) {
        return productoIngredienteRepository.save(productoIngrediente);
    }

    // Eliminar una relación entre producto e ingrediente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable Integer id) {
        if (productoIngredienteRepository.existsById(id)) {
            productoIngredienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
