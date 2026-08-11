package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class DatosContactoDireccion {

    private final String calle;
    private final String ciudad;
    private final String provincia;
    private final String codigoPostal;

    public DatosContactoDireccion(String calle, String ciudad, String provincia, String codigoPostal) {
        validacion(calle, ciudad, provincia, codigoPostal);

        this.calle = calle.trim();
        this.ciudad = ciudad.trim();
        this.provincia = provincia.trim();
        this.codigoPostal = codigoPostal.trim();
    }

    private void validacion(String calle, String ciudad, String provincia, String codigoPostal) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException(
                    "La calle es obligatoria"
            );
        }

        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException(
                    "La ciudad es obligatoria"
            );
        }

        if (provincia == null || provincia.isBlank()) {
            throw new IllegalArgumentException(
                    "La provincia es obligatoria"
            );
        }

        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException(
                    "El código postal es obligatorio"
            );
        }

        if (!codigoPostal.trim().matches("\\d{5}")) {
            throw new IllegalArgumentException(
                    "El código postal debe contener 5 números"
            );
        }
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        DatosContactoDireccion otraDireccion = (DatosContactoDireccion) objeto;

        return Objects.equals(calle, otraDireccion.calle)
                && Objects.equals(ciudad, otraDireccion.ciudad)
                && Objects.equals(provincia, otraDireccion.provincia)
                && Objects.equals(codigoPostal, otraDireccion.codigoPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                calle,
                ciudad,
                provincia,
                codigoPostal
        );
    }

    @Override
    public String toString() {
        return calle + ", "
                + codigoPostal + ", "
                + ciudad + ", "
                + provincia;
    }
}