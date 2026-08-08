package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class OficinaDireccion {

    private final String codigoPostal;
    private final String ciudad;
    private final String calle;
    private final String numero;

    public OficinaDireccion(
            String codigoPostal,
            String ciudad,
            String calle,
            String numero
    ) {
        validarCodigoPostal(codigoPostal);
        validarCiudad(ciudad);
        validarCalle(calle);
        validarNumero(numero);

        this.codigoPostal = codigoPostal.trim();
        this.ciudad = ciudad.trim();
        this.calle = calle.trim();
        this.numero = numero.trim();
    }

    private void validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException(
                    "El código postal es obligatorio"
            );
        }

        if (!codigoPostal.trim().matches("\\d{5}")) {
            throw new IllegalArgumentException(
                    "El código postal debe contener exactamente 5 números"
            );
        }
    }

    private void validarCiudad(String ciudad) {
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException(
                    "La ciudad es obligatoria"
            );
        }

        if (ciudad.trim().length() < 2) {
            throw new IllegalArgumentException(
                    "La ciudad debe tener al menos 2 caracteres"
            );
        }

        if (ciudad.trim().length() > 100) {
            throw new IllegalArgumentException(
                    "La ciudad no puede superar los 100 caracteres"
            );
        }
    }

    private void validarCalle(String calle) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException(
                    "La calle es obligatoria"
            );
        }

        if (calle.trim().length() < 3) {
            throw new IllegalArgumentException(
                    "La calle debe tener al menos 3 caracteres"
            );
        }

        if (calle.trim().length() > 150) {
            throw new IllegalArgumentException(
                    "La calle no puede superar los 150 caracteres"
            );
        }
    }

    private void validarNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException(
                    "El número es obligatorio"
            );
        }

        if (numero.trim().length() > 20) {
            throw new IllegalArgumentException(
                    "El número no puede superar los 20 caracteres"
            );
        }

        if (!numero.trim().matches("[\\p{L}\\d /.-]+")) {
            throw new IllegalArgumentException(
                    "El número contiene caracteres no permitidos"
            );
        }
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        OficinaDireccion otra = (OficinaDireccion) objeto;

        return Objects.equals(codigoPostal, otra.codigoPostal)
                && Objects.equals(ciudad, otra.ciudad)
                && Objects.equals(calle, otra.calle)
                && Objects.equals(numero, otra.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                codigoPostal,
                ciudad,
                calle,
                numero
        );
    }

    @Override
    public String toString() {
        return calle
                + ", "
                + numero
                + ", "
                + ciudad
                + ", "
                + codigoPostal;
    }
}