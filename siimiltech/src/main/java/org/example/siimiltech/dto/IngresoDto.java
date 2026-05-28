package org.example.siimiltech.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngresoDto {


        private Long id;
        private LocalDateTime fechaIngreso;
        private String placa;

}
