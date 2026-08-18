package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CrearTarifaRequest(
        @NotNull(message = "La provincia es obligatoria")
        UUID provinciaOrigenId,

        @NotNull(message = "La provincia es obligatoria")
        UUID provinciaDestinoId,

        @NotNull(message = "La provincia es obligatoria")
        BigDecimal precioPorKilogramo


) {




}
