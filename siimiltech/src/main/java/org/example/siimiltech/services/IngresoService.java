package org.example.siimiltech.services;

import org.example.siimiltech.entity.Ingreso;
import org.example.siimiltech.entity.Vehiculo;
import org.example.siimiltech.repos.VehiculoRepo;
import org.example.siimiltech.repos.IngresoRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IngresoService {

    private final IngresoRepo ingresoRepo;
    private final VehiculoRepo vehiculoRepo;
    private final EmailService emailService = new EmailService();

    public IngresoService(IngresoRepo ingresoRepo, VehiculoRepo vehiculoRepo) {
        this.ingresoRepo = ingresoRepo;
        this.vehiculoRepo = vehiculoRepo;
    }

    public Ingreso registrarIngreso(String placa) {
        System.out.printf("Registrando ingreso para la placa: %s\n", placa);
        Vehiculo vehiculo = vehiculoRepo.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no existe"));

        Ingreso ingreso = new Ingreso();
        ingreso.setVehiculo(vehiculo);
        ingreso.setFechaIngreso(LocalDateTime.now());

        return ingresoRepo.save(ingreso);
    }

    public Ingreso registrarSalida(Long id) {

        Ingreso ingreso = ingresoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));

        LocalDateTime salida = LocalDateTime.now();
        ingreso.setFechaSalida(salida);

        long minutos = java.time.Duration.between(
                ingreso.getFechaIngreso(),
                salida
        ).toMinutes();

        if (minutos <= 0) {
            minutos = 1;
        }

        long tarifa = minutos * 50;

        ingresoRepo.save(ingreso);

        String cuerpoCorreo = """
        Salida de vehículo registrada

        Placa: %s
        Tipo: %s
        Tiempo total: %d minutos
        Valor pagado: $%d
    """.formatted(
                ingreso.getVehiculo().getPlaca(),
                ingreso.getVehiculo().getTipo(),
                minutos,
                tarifa
        );

        emailService.enviarCorreo(ingreso.getVehiculo().getPlaca(),
                ingreso.getVehiculo().getTipo(),
                minutos,
                tarifa);

        return ingreso;
    }

    public List<Ingreso> listar() {
        return ingresoRepo.findAll();
    }
}