package org.example.siimiltech.mapper;

import org.example.siimiltech.dto.IngresoDto;
import org.example.siimiltech.entity.Ingreso;
import org.example.siimiltech.entity.Vehiculo;
import org.example.siimiltech.dto.VehiculoDto;

public class Mapper {

    public static VehiculoDto toDTO(Vehiculo v) {
        VehiculoDto dto = new VehiculoDto();
        dto.setId(v.getId());
        dto.setPlaca(v.getPlaca());
        dto.setTipo(v.getTipo());
        return dto;
    }

    public static Vehiculo toEntity(VehiculoDto dto) {
        Vehiculo v = new Vehiculo();
        v.setId(dto.getId());
        v.setPlaca(dto.getPlaca());
        v.setTipo(dto.getTipo());
        return v;
    }


    public static IngresoDto toDTO(Ingreso i) {
        IngresoDto dto = new IngresoDto();
        dto.setId(i.getId());
        dto.setFechaIngreso(i.getFechaIngreso());

        if (i.getVehiculo() != null) {
            dto.setPlaca(i.getVehiculo().getPlaca());
        }

        return dto;
    }

    public static Ingreso toEntity(IngresoDto dto, Vehiculo vehiculo) {
        Ingreso i = new Ingreso();
        i.setId(dto.getId());
        i.setFechaIngreso(dto.getFechaIngreso());
        i.setVehiculo(vehiculo);
        return i;
    }


}
