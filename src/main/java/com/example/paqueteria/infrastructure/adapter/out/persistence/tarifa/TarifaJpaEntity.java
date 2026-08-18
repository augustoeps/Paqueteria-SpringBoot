package com.example.paqueteria.infrastructure.adapter.out.persistence.tarifa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tarifa")
public class TarifaJpaEntity {

    @Id
    private UUID id;

    @Column(name = "provincia_origen_id", nullable = false)
    private UUID provinciaOrigenId;

    @Column(name = "provincia_destino_id", nullable = false)
    private UUID provinciaDestinoId;

    @Column(name = "precio_por_kilogramo", nullable = false)
    private BigDecimal precioPorKilogramo;

    // JPA EXIGE un constructor vacío (sin argumentos)
    protected TarifaJpaEntity() {
    }

    // Constructor propio para cuando tú la construyas manualmente en el Mapper
    public TarifaJpaEntity(UUID id, UUID provinciaOrigenId, UUID provinciaDestinoId, BigDecimal precioPorKilogramo) {
        this.id = id;
        this.provinciaOrigenId = provinciaOrigenId;
        this.provinciaDestinoId = provinciaDestinoId;
        this.precioPorKilogramo = precioPorKilogramo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProvinciaOrigenId() {
        return provinciaOrigenId;
    }

    public void setProvinciaOrigenId(UUID provinciaOrigenId) {
        this.provinciaOrigenId = provinciaOrigenId;
    }

    public UUID getProvinciaDestinoId() {
        return provinciaDestinoId;
    }

    public void setProvinciaDestinoId(UUID provinciaDestinoId) {
        this.provinciaDestinoId = provinciaDestinoId;
    }

    public BigDecimal getPrecioPorKilogramo() {
        return precioPorKilogramo;
    }

    public void setPrecioPorKilogramo(BigDecimal precioPorKilogramo) {
        this.precioPorKilogramo = precioPorKilogramo;
    }
}