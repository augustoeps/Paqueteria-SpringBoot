package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public Record FindOrigenyDestinoTarifaRequest(
        @NotNull(message = "La provincia es obligatoria") UUID provinciaOrigenId,
        @NotNull(message = "La provincia es obligatoria") UUID provinciaDestinoId
) {
}
