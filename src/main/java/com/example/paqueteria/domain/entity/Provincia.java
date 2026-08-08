package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;

import java.util.Objects;
import java.util.UUID;

public class Provincia {

    private final UUID id;
    private ProvinciaNombre nombre;

    /**
     * Constructor para crear una provincia nueva.
     * El identificador se genera automáticamente.
     */
    public Provincia(ProvinciaNombre nombre) {
        validarNombre(nombre);

        this.id = UUID.randomUUID();
        this.nombre = nombre;
    }

    /**
     * Constructor para reconstruir una provincia existente,
     * por ejemplo, cuando se obtiene de la base de datos.
     */
    public Provincia(
            UUID id,
            ProvinciaNombre nombre
    ) {
        validarId(id);
        validarNombre(nombre);

        this.id = id;
        this.nombre = nombre;
    }

    private void validarId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El identificador de la provincia es obligatorio"
            );
        }
    }

    private void validarNombre(ProvinciaNombre nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException(
                    "El nombre de la provincia es obligatorio"
            );
        }
    }

    public UUID getId() {
        return id;
    }

    public ProvinciaNombre getNombre() {
        return nombre;
    }

    public void cambiarNombre(ProvinciaNombre nuevoNombre) {
        validarNombre(nuevoNombre);
        this.nombre = nuevoNombre;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Provincia otraProvincia = (Provincia) objeto;

        return Objects.equals(id, otraProvincia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Provincia{" +
                "id=" + id +
                ", nombre=" + nombre +
                '}';
    }
}