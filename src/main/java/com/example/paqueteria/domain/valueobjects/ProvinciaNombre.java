package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class ProvinciaNombre {

    private final String nombre;

    public ProvinciaNombre(String nombre) {
        validar(nombre);
        this.nombre = nombre.trim();
    }

    private void validar(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException(
                    "El nombre de la provincia no puede ser null"
            );
        }

        if (nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la provincia es obligatorio"
            );
        }

        if (nombre.trim().length() <= 3) {
            throw new IllegalArgumentException(
                    "El nombre de la provincia debe tener más de 3 caracteres"
            );
        }
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        ProvinciaNombre otro = (ProvinciaNombre) objeto;

        return Objects.equals(nombre, otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}