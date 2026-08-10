package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class Tarifa {

    private final UUID id;
    private final UUID provinciaOrigenId;
    private final UUID provinciaDestinoId;
    private TarifaPrecioPorKilogramo precioPorKilogramo;

    /**
     * Constructor para crear una tarifa nueva.
     */
    public Tarifa(UUID provinciaOrigenId, UUID provinciaDestinoId, TarifaPrecioPorKilogramo precioPorKilogramo) {
        validarDatos(provinciaOrigenId, provinciaDestinoId, precioPorKilogramo);

        this.id = UUID.randomUUID();
        this.provinciaOrigenId = provinciaOrigenId;
        this.provinciaDestinoId = provinciaDestinoId;
        this.precioPorKilogramo = precioPorKilogramo;
    }

    /**
     * Constructor para reconstruir una tarifa existente.
     */
    public Tarifa(UUID id, UUID provinciaOrigenId, UUID provinciaDestinoId,TarifaPrecioPorKilogramo precioPorKilogramo
    ) {
        validarId(id);

        validarDatos(provinciaOrigenId, provinciaDestinoId, precioPorKilogramo);

        this.id = id;
        this.provinciaOrigenId = provinciaOrigenId;
        this.provinciaDestinoId = provinciaDestinoId;
        this.precioPorKilogramo = precioPorKilogramo;
    }

    private void validarId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El identificador de la tarifa es obligatorio"
            );
        }
    }

    private void validarDatos(UUID provinciaOrigenId, UUID provinciaDestinoId,TarifaPrecioPorKilogramo precioPorKilogramo) {
        if (provinciaOrigenId == null) {
            throw new IllegalArgumentException(
                    "La provincia de origen es obligatoria"
            );
        }

        if (provinciaDestinoId == null) {
            throw new IllegalArgumentException(
                    "La provincia de destino es obligatoria"
            );
        }

        if (precioPorKilogramo == null) {
            throw new IllegalArgumentException(
                    "El precio por kilogramo es obligatorio"
            );
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getProvinciaOrigenId() {
        return provinciaOrigenId;
    }

    public UUID getProvinciaDestinoId() {
        return provinciaDestinoId;
    }

    public TarifaPrecioPorKilogramo getPrecioPorKilogramo() {
        return precioPorKilogramo;
    }

    public BigDecimal calcularPrecio(BigDecimal pesoKilogramos) {
        if (pesoKilogramos == null) {
            throw new IllegalArgumentException(
                    "El peso es obligatorio"
            );
        }

        if (pesoKilogramos.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que cero"
            );
        }
        return precioPorKilogramo.getPrecioPorKilogramo().multiply(pesoKilogramos).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Tarifa otraTarifa = (Tarifa) objeto;

        return Objects.equals(id, otraTarifa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}