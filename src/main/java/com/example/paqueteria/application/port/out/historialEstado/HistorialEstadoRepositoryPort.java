package com.example.paqueteria.application.port.out.historialEstado;

import com.example.paqueteria.domain.entity.HistorialEstado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistorialEstadoRepositoryPort {
    HistorialEstado save(HistorialEstado historialEstado);
    List<HistorialEstado> findByPaqueteId(UUID paqueteId);
    Optional<HistorialEstado> findById(UUID id);
}