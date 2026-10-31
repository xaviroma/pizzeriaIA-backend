package com.jm.controller;

import com.jm.model.Ingrediente;
import com.jm.repository.IngredienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteController(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    @GetMapping
    public List<Ingrediente> obtenerIngredientes() {
        return ingredienteRepository.findAll();
    }

    @PostMapping
    public Ingrediente crearIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    @GetMapping("/{id}")
    public Ingrediente obtenerIngredientePorId(@PathVariable int id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Ingrediente actualizarIngrediente(@PathVariable int id, @RequestBody Ingrediente ingrediente) {
        ingrediente.setId(id);
        return ingredienteRepository.save(ingrediente);
    }

    @DeleteMapping("/{id}")
    public void eliminarIngrediente(@PathVariable int id) {
        ingredienteRepository.deleteById(id);
    }
}
