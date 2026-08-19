package com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto;

import com.example.paqueteria.domain.valueobjects.DatosContacto;

public record DatosContactoResponse(
        String nombre,
        String telefono,
        String email,
        DireccionContactoResponse direccion
) {
    public static DatosContactoResponse desde(DatosContacto datosContacto) {
        return new DatosContactoResponse(
                datosContacto.getNombre(),
                datosContacto.getTelefono(),
                datosContacto.getEmail(),
                DireccionContactoResponse.desde(datosContacto.getDireccion())
        );
    }
}