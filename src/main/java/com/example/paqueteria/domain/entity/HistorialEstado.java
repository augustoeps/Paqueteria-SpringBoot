package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.enums.EstadoPaquete;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class HistorialEstado {

    private final UUID id;
    private final UUID paqueteId;
    private final EstadoPaquete estadoPaquete;
    private final LocalDateTime fecha;
    private final UUID oficinaId;

    public HistorialEstado(UUID paqueteId, EstadoPaquete estadoPaquete, LocalDateTime fecha, UUID oficinaId) {
        validacion(paqueteId, estadoPaquete, fecha);

        this.id = UUID.randomUUID();
        this.paqueteId = paqueteId;
        this.estadoPaquete = estadoPaquete;
        this.fecha = fecha;
        this.oficinaId = oficinaId;
    }

    public HistorialEstado(UUID id, UUID paqueteId, EstadoPaquete estadoPaquete, LocalDateTime fecha, UUID oficinaId) {
        validacionId(id);
        validacion(paqueteId, estadoPaquete, fecha);

        this.id = id;
        this.paqueteId = paqueteId;
        this.estadoPaquete = estadoPaquete;
        this.fecha = fecha;
        this.oficinaId = oficinaId;
    }

    private void validacionId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El identificador del historial es obligatorio"
            );
        }
    }

    private void validacion(UUID paqueteId, EstadoPaquete estadoPaquete, LocalDateTime fecha) {
        if (paqueteId == null) {
            throw new IllegalArgumentException(
                    "El identificador del paquete es obligatorio"
            );
        }

        if (estadoPaquete == null) {
            throw new IllegalArgumentException(
                    "El estado del paquete es obligatorio"
            );
        }

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha del cambio de estado es obligatoria"
            );
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getPaqueteId() {
        return paqueteId;
    }

    public EstadoPaquete getEstadoPaquete() {
        return estadoPaquete;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public UUID getOficinaId() {
        return oficinaId;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        HistorialEstado otroHistorial = (HistorialEstado) objeto;

        return Objects.equals(id, otroHistorial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}