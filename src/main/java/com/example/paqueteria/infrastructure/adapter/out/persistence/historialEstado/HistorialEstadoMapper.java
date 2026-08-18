package com.example.paqueteria.infrastructure.adapter.out.persistence.historialEstado;

import com.example.paqueteria.domain.entity.HistorialEstado;
import org.springframework.stereotype.Component;

@Component
public class HistorialEstadoMapper {

    public HistorialEstadoJpaEntity toJpaEntity(HistorialEstado historialEstado) {
        return new HistorialEstadoJpaEntity(
                historialEstado.getId(),
                historialEstado.getPaqueteId(),
                historialEstado.getEstadoPaquete(),
                historialEstado.getFecha(),
                historialEstado.getOficinaId()
        );
    }

    public HistorialEstado toDomain(HistorialEstadoJpaEntity historialEstadoJpa) {
        return new HistorialEstado(
                historialEstadoJpa.getId(),
                historialEstadoJpa.getPaqueteId(),
                historialEstadoJpa.getEstadoPaquete(),
                historialEstadoJpa.getFecha(),
                historialEstadoJpa.getOficinaId()
        );
    }
}