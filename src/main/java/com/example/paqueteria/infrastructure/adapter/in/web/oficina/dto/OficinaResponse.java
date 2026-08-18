package com.example.paqueteria.infrastructure.adapter.in.web.oficina.dto;

import com.example.paqueteria.domain.entity.Oficina;

import java.util.UUID;

public record OficinaResponse(UUID id, String codigo, String nombre, String calle, String numero, String ciudad, String codigoPostal, UUID provinciaId) {

    public static OficinaResponse desde(Oficina oficina) {
        return new OficinaResponse(
                oficina.getId(),
                oficina.getCodigo().getCodigo(),
                oficina.getNombre().getNombre(),
                oficina.getDireccion().getCalle(),
                oficina.getDireccion().getNumero(),
                oficina.getDireccion().getCiudad(),
                oficina.getDireccion().getCodigoPostal(),
                oficina.getProvinciaId()
        );
    }
}