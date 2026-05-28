package org.example.siimiltech.dto;

import lombok.Data;
import org.example.siimiltech.entity.Vehiculo;

@Data
public class VehiculoResponse {

    private Vehiculo vehiculo;
    private boolean yaExistia;
    private String mensaje;
}
