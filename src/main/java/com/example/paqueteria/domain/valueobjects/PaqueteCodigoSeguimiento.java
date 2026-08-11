package com.example.paqueteria.domain.valueobjects;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class PaqueteCodigoSeguimiento {

    private final String codigo;

    public PaqueteCodigoSeguimiento(String codigo) {
        String codigoNormalizado = normalizar(codigo);
        validacion(codigoNormalizado);

        this.codigo = codigoNormalizado;
    }

    private String normalizar(String codigo) {
        if (codigo == null) {
            return null;
        }

        return codigo.trim().toUpperCase();
    }

    private void validacion(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de seguimiento es obligatorio"
            );
        }

        if (!codigo.matches("PAQ-\\d{4}-\\d{6}")) {
            throw new IllegalArgumentException(
                    "El código debe tener el formato PAQ-YYYY-NNNNNN"
            );
        }
    }

    public static PaqueteCodigoSeguimiento generar() {
        int anio = LocalDate.now().getYear();

        int numero = ThreadLocalRandom.current()
                .nextInt(0, 1_000_000);

        String codigo = String.format(
                "PAQ-%d-%06d",
                anio,
                numero
        );

        return new PaqueteCodigoSeguimiento(codigo);
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

        PaqueteCodigoSeguimiento otroCodigo =
                (PaqueteCodigoSeguimiento) objeto;

        return Objects.equals(codigo, otroCodigo.codigo);
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