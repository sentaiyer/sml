package org.example.siimiltech.repos;

import org.example.siimiltech.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VehiculoRepo extends JpaRepository<Vehiculo, String> {

    Optional<Vehiculo> findByPlaca(String placa);

}