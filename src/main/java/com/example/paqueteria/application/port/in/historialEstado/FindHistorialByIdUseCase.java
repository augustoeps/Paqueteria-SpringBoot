package com.example.paqueteria.application.port.in.historialEstado;

import com.example.paqueteria.domain.entity.HistorialEstado;

import java.util.Optional;
import java.util.UUID;

public interface FindHistorialByIdUseCase {
    Optional<HistorialEstado> findById(UUID id);
}