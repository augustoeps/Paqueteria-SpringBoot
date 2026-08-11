package com.example.paqueteria.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class PaquetePeso {

    private final BigDecimal kilogramos;

    public PaquetePeso(BigDecimal kilogramos) {
        BigDecimal pesoNormalizado = normalizar(kilogramos);
        validacion(pesoNormalizado);

        this.kilogramos = pesoNormalizado;
    }

    private BigDecimal normalizar(BigDecimal kilogramos) {
        if (kilogramos == null) {
            return null;
        }

        return kilogramos.setScale(
                3,
                RoundingMode.HALF_UP
        );
    }

    private void validacion(BigDecimal kilogramos) {
        if (kilogramos == null) {
            throw new IllegalArgumentException(
                    "El peso es obligatorio"
            );
        }

        if (kilogramos.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que cero"
            );
        }
    }

    public BigDecimal getKilogramos() {
        return kilogramos;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        PaquetePeso otroPeso = (PaquetePeso) objeto;

        return Objects.equals(
                kilogramos,
                otroPeso.kilogramos
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(kilogramos);
    }

    @Override
    public String toString() {
        return kilogramos + " kg";
    }
}