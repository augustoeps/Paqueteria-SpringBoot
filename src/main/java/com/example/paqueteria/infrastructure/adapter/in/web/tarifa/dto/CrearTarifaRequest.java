package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CrearTarifaRequest(
        @NotNull(message = "La provincia origen es obligatoria")
        UUID provinciaOrigenId,

        @NotNull(message = "La provincia destino es obligatoria")
        UUID provinciaDestinoId,

        @NotNull(message = "El precio por kilogramo es obligatorio")
        @Positive(message = "El precio debe ser mayor que cero")
        BigDecimal precioPorKilogramo
) {
}