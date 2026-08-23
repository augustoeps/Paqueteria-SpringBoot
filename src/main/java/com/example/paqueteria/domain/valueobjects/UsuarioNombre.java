package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class UsuarioNombre {

    private final String nombre;

    public UsuarioNombre(String nombre) {
        String nombreNormalizado = normalizar(nombre);
        validacion(nombreNormalizado);
        this.nombre = nombreNormalizado;
    }

    private String normalizar(String nombre) {
        if (nombre == null) {
            return null;
        }
        return nombre.trim();
    }

    private void validacion(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (nombre.length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioNombre that)) return false;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}