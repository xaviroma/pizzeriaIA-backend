package com.jm.controller;

import com.jm.model.Producto;
import com.jm.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable int id) {
        return productoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable int id) {
        productoRepository.deleteById(id);
    }
}
