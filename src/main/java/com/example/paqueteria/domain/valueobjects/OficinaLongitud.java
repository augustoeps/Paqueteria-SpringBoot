package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class OficinaLongitud {

    private final Double valor;

    public OficinaLongitud(Double valor) {
        validacion(valor);
        this.valor = valor;
    }

    private void validacion(Double valor) {
        if (valor == null) {
            throw new IllegalArgumentException("La longitud es obligatoria");
        }
        if (valor < -180 || valor > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180 grados");
        }
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficinaLongitud that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}