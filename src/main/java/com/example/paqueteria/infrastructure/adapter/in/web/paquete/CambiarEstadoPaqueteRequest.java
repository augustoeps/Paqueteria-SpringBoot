package com.example.paqueteria.infrastructure.adapter.in.web.paquete;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CambiarEstadoPaqueteRequest(
        @NotNull(message = "La oficina es obligatoria")
        UUID oficinaId
) {
}