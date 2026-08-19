package com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto;

import com.example.paqueteria.domain.valueobjects.DatosContactoDireccion;

public record DireccionContactoResponse(
        String calle,
        String ciudad,
        String provincia,
        String codigoPostal
) {
    public static DireccionContactoResponse desde(DatosContactoDireccion direccion) {
        return new DireccionContactoResponse(
                direccion.getCalle(),
                direccion.getCiudad(),
                direccion.getProvincia(),
                direccion.getCodigoPostal()
        );
    }
}