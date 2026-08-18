package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;

import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Tarifa;

import java.math.BigDecimal;
import java.util.UUID;

public record TarifaResponse(UUID id, UUID provinciaOrigenId, UUID provinciaDestinoId, BigDecimal precioPorKilogramo) {

    public static TarifaResponse desde(Tarifa tarifa) {
        return new TarifaResponse(
                tarifa.getId(),
                tarifa.getProvinciaOrigenId(),
                tarifa.getProvinciaDestinoId(),
                tarifa.getPrecioPorKilogramo().getPrecioPorKilogramo()
        );
    }
}