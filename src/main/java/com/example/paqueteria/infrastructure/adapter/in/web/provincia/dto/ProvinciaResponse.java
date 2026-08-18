package com.example.paqueteria.infrastructure.adapter.in.web.provincia.dto;

import com.example.paqueteria.domain.entity.Provincia;

import java.util.UUID;

public record ProvinciaResponse(UUID id, String nombre) {

    public static ProvinciaResponse desde(Provincia provincia) {
        return new ProvinciaResponse(
                provincia.getId(),
                provincia.getNombre().getNombre()
        );
    }
}
