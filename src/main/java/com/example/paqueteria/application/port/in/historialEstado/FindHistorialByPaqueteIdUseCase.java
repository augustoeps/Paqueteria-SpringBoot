package com.example.paqueteria.application.port.in.historialEstado;

import com.example.paqueteria.domain.entity.HistorialEstado;

import java.util.List;
import java.util.UUID;

public interface FindHistorialByPaqueteIdUseCase {
    List<HistorialEstado> findByPaqueteId(UUID paqueteId);
}