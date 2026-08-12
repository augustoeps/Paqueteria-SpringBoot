package com.example.paqueteria.application.port.out.paquete;

import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaqueteRepositoryPort {

    Paquete save(Paquete paquete);
    List<Paquete> findAll();
    Optional<Paquete> findById(UUID id);
    Optional<Paquete> findByCodigoSeguimiento(PaqueteCodigoSeguimiento codigoSeguimiento);

}
