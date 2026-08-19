package com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateTarifaRequest(
        @NotNull(message = "El precio por kilogramo es obligatorio")
        BigDecimal precioPorKilogramo
) {
}
