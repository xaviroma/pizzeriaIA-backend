package com.jm.controller;

import com.jm.model.Categoria;
import com.jm.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @GetMapping("/{id}")
    public Categoria obtenerCategoriaPorId(@PathVariable int id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Categoria actualizarCategoria(@PathVariable int id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable int id) {
        categoriaRepository.deleteById(id);
    }
}
