package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;

import java.util.Optional;
import java.util.UUID;

public interface FindTarifaByIdUseCase {
    Optional<Tarifa> findById(UUID id);
}
