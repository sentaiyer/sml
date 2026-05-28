package org.example.siimiltech.controllers;

import org.example.siimiltech.dto.VehiculoResponse;
import org.example.siimiltech.entity.Vehiculo;
import org.example.siimiltech.services.VehiculoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @PostMapping
    public VehiculoResponse crear(@RequestBody Vehiculo v) {
        return service.guardar(v);
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return service.listar();
    }

    @GetMapping("/{placa}")
    public Vehiculo buscar(@PathVariable String placa) {
        return service.buscarPorPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    @DeleteMapping("/{placa}")
    public void eliminar(@PathVariable String placa) {
        service.eliminar(placa);
    }
}