package com.example.paqueteria.infrastructure.adapter.in.web.historialEstado.dto;

import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.enums.EstadoPaquete;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistorialEstadoResponse(
        UUID id,
        UUID paqueteId,
        EstadoPaquete estadoPaquete,
        LocalDateTime fecha,
        UUID oficinaId
) {
    public static HistorialEstadoResponse desde(HistorialEstado historialEstado) {
        return new HistorialEstadoResponse(
                historialEstado.getId(),
                historialEstado.getPaqueteId(),
                historialEstado.getEstadoPaquete(),
                historialEstado.getFecha(),
                historialEstado.getOficinaId()
        );
    }
}