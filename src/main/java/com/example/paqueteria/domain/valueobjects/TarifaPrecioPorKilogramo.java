package com.example.paqueteria.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class TarifaPrecioPorKilogramo {

    private final BigDecimal precioPorKilogramo;

    public TarifaPrecioPorKilogramo(BigDecimal precioPorKilogramo) {
        validar(precioPorKilogramo);

        this.precioPorKilogramo = precioPorKilogramo.setScale(2,RoundingMode.HALF_UP);
    }

    private void validar(BigDecimal precioPorKilogramo) {
        if (precioPorKilogramo == null) {
            throw new IllegalArgumentException(
                    "El precio por kilogramo es obligatorio"
            );
        }

        if (
                precioPorKilogramo.compareTo(BigDecimal.ZERO)
                        <= 0
        ) {
            throw new IllegalArgumentException(
                    "El precio por kilogramo debe ser mayor que cero"
            );
        }
    }

    public BigDecimal getPrecioPorKilogramo() {
        return precioPorKilogramo;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        TarifaPrecioPorKilogramo otro = (TarifaPrecioPorKilogramo) objeto;

        return Objects.equals(precioPorKilogramo,otro.precioPorKilogramo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(precioPorKilogramo);
    }

    @Override
    public String toString() {
        return precioPorKilogramo + " EUR/kg";
    }
}