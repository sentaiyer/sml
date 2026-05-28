package org.example.siimiltech.repos;

import org.example.siimiltech.entity.Ingreso;
import org.example.siimiltech.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngresoRepo extends JpaRepository<Ingreso, Long> {

}