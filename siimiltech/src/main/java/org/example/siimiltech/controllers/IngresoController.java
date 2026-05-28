package org.example.siimiltech.controllers;
import org.example.siimiltech.entity.Ingreso;
import org.example.siimiltech.services.IngresoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingresos")
public class IngresoController {

    private final IngresoService service;

    public IngresoController(IngresoService service) {
        this.service = service;
    }

    // Registrar ingreso por placa
    @PostMapping("/placa/{placa}")
    public Ingreso registrar(@PathVariable String placa) {
        return service.registrarIngreso(placa);
    }

    @PutMapping("/salida/{id}")
    public Ingreso registrarSalida(@PathVariable long id) {
        return service.registrarSalida(id);
    }

    @GetMapping
    public List<Ingreso> listar() {
        return service.listar();
    }
}