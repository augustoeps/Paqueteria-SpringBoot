package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class DatosContacto {

    private final String nombre;
    private final String telefono;
    private final String email;
    private final DatosContactoDireccion direccion;

    public DatosContacto(String nombre, String telefono, String email, DatosContactoDireccion direccion) {
        validacion(nombre, telefono, email, direccion);

        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
        this.email = email.trim().toLowerCase();
        this.direccion = direccion;
    }

    private void validacion(String nombre, String telefono, String email, DatosContactoDireccion direccion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio"
            );
        }

        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono es obligatorio"
            );
        }

        if (!telefono.trim().matches("[+\\d ()-]{7,20}")) {
            throw new IllegalArgumentException(
                    "El teléfono no tiene un formato válido"
            );
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El email es obligatorio"
            );
        }

        if (!email.trim().matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException(
                    "El email no tiene un formato válido"
            );
        }

        if (direccion == null) {
            throw new IllegalArgumentException(
                    "La dirección es obligatoria"
            );
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public DatosContactoDireccion getDireccion() {
        return direccion;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        DatosContacto otrosDatos = (DatosContacto) objeto;

        return Objects.equals(nombre, otrosDatos.nombre)
                && Objects.equals(telefono, otrosDatos.telefono)
                && Objects.equals(email, otrosDatos.email)
                && Objects.equals(direccion, otrosDatos.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                nombre,
                telefono,
                email,
                direccion
        );
    }
}