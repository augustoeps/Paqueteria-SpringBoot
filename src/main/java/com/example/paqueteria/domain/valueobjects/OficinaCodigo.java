package com.example.paqueteria.domain.valueobjects;

import java.util.Locale;
import java.util.Objects;

public final class OficinaCodigo {

    private final String codigo;

    public OficinaCodigo(String codigo) {
        String codigoNormalizado = normalizar(codigo);
        validar(codigoNormalizado);
        this.codigo = codigoNormalizado;
    }

    private String normalizar(String codigo) {
        if (codigo == null) {
            return null;
        }

        return codigo
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private void validar(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de la oficina es obligatorio"
            );
        }

        if (!codigo.matches("[A-Z]{3}\\d{2}")) {
            throw new IllegalArgumentException(
                    "El código de la oficina debe tener el formato MAD05"
            );
        }
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        OficinaCodigo otro = (OficinaCodigo) objeto;

        return Objects.equals(codigo, otro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}