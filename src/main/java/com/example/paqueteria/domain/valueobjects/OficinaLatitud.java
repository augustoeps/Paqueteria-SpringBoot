package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public class OficinaLatitud {
    private final Double valor;

    public OficinaLatitud(Double valor) {
        validacion(valor);
        this.valor = valor;
    }

    private void validacion(Double valor) {
        if (valor == null) {
            throw new IllegalArgumentException("La latitud es obligatoria");
        }
        if (valor < -90 || valor > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90 grados");
        }
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficinaLatitud that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
