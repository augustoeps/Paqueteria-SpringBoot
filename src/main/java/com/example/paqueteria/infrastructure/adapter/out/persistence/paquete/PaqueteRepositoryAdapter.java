package com.example.paqueteria.infrastructure.adapter.out.persistence.paquete;

import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaqueteRepositoryAdapter implements PaqueteRepositoryPort {
    @Override
    public Paquete save(Paquete paquete) {
        return null;
    }

    @Override
    public List<Paquete> findAll() {
        return List.of();
    }

    @Override
    public Optional<Paquete> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Paquete> findByCodigoSeguimiento(PaqueteCodigoSeguimiento codigoSeguimiento) {
        return Optional.empty();
    }
}
