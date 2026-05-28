package org.example.siimiltech.services;

import org.example.siimiltech.dto.VehiculoResponse;
import org.example.siimiltech.entity.Vehiculo;
import org.example.siimiltech.repos.VehiculoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepo repo;
    private final IngresoService service;

    public VehiculoService(VehiculoRepo repo, IngresoService service) {
        this.repo = repo;
        this.service = service;
    }

    public VehiculoResponse guardar(Vehiculo vehiculo) {

        boolean existe = repo.findByPlaca(vehiculo.getPlaca()).isPresent();

        Vehiculo v = repo.findByPlaca(vehiculo.getPlaca())
                .orElseGet(() -> repo.save(vehiculo));

        VehiculoResponse response = new VehiculoResponse();
        response.setVehiculo(v);
        response.setYaExistia(existe);
        service.registrarIngreso(vehiculo.getPlaca());
        response.setMensaje(
                existe
                        ? "El vehículo ya existía"
                        : "Vehículo creado correctamente"
        );


        return response;
    }

    public List<Vehiculo> listar() {
        return repo.findAll();
    }

    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        return repo.findById(placa);
    }

    public void eliminar(String placa) {
        repo.deleteById(placa);
    }
}