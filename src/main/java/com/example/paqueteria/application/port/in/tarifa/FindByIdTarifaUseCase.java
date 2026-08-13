package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;

import java.util.Optional;
import java.util.UUID;

public interface FindByIdTarifaByIdUseCase {
    Optional<Tarifa> findById(UUID id);
}
