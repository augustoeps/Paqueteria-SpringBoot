package com.example.paqueteria.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class PaqueteTarifaAplicada {

    private final BigDecimal monto;

    public PaqueteTarifaAplicada(BigDecimal monto) {
        BigDecimal montoNormalizado = normalizar(monto);
        validacion(montoNormalizado);

        this.monto = montoNormalizado;
    }

    private BigDecimal normalizar(BigDecimal monto) {
        if (monto == null) {
            return null;
        }

        return monto.setScale(
                2,
                RoundingMode.HALF_UP
        );
    }

    private void validacion(BigDecimal monto) {
        if (monto == null) {
            throw new IllegalArgumentException(
                    "El monto de la tarifa es obligatorio"
            );
        }

        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El monto de la tarifa no puede ser negativo"
            );
        }
    }

    public BigDecimal getMonto() {
        return monto;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        PaqueteTarifaAplicada otraTarifa =
                (PaqueteTarifaAplicada) objeto;

        return Objects.equals(monto, otraTarifa.monto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto);
    }

    @Override
    public String toString() {
        return monto + " EUR";
    }
}