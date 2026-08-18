package com.example.paqueteria.infrastructure.adapter.out.persistence.historialEstado;

import com.example.paqueteria.domain.enums.EstadoPaquete;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historial_estado")
public class HistorialEstadoJpaEntity {

    @Id
    private UUID id;

    @Column(name = "paquete_id", nullable = false)
    private UUID paqueteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_paquete", nullable = false)
    private EstadoPaquete estadoPaquete;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "oficina_id", nullable = false)
    private UUID oficinaId;

    // JPA exige un constructor vacío
    protected HistorialEstadoJpaEntity() {
    }

    // Constructor completo para el Mapper
    public HistorialEstadoJpaEntity(UUID id, UUID paqueteId, EstadoPaquete estadoPaquete, LocalDateTime fecha, UUID oficinaId) {
        this.id = id;
        this.paqueteId = paqueteId;
        this.estadoPaquete = estadoPaquete;
        this.fecha = fecha;
        this.oficinaId = oficinaId;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(UUID paqueteId) {
        this.paqueteId = paqueteId;
    }

    public EstadoPaquete getEstadoPaquete() {
        return estadoPaquete;
    }

    public void setEstadoPaquete(EstadoPaquete estadoPaquete) {
        this.estadoPaquete = estadoPaquete;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public UUID getOficinaId() {
        return oficinaId;
    }

    public void setOficinaId(UUID oficinaId) {
        this.oficinaId = oficinaId;
    }
}