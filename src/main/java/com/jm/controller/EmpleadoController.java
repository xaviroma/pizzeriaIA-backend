package com.jm.controller;

import com.jm.model.Empleado;
import com.jm.repository.EmpleadoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping
    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleadoPorId(@PathVariable int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable int id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable int id) {
        empleadoRepository.deleteById(id);
    }
}
