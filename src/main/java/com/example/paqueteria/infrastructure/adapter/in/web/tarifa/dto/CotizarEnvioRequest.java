package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CotizarEnvioRequest(
        @NotNull UUID provinciaOrigenId,
        @NotNull UUID provinciaDestinoId,
        @NotNull BigDecimal peso
) {
}