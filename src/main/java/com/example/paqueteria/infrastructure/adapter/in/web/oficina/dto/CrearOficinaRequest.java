package com.example.paqueteria.infrastructure.adapter.in.web.oficina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CrearOficinaRequest(
        @NotBlank(message = "El código es obligatorio")
        String codigo,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La calle es obligatoria")
        String calle,

        @NotBlank(message = "El número es obligatorio")
        String numero,

        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad,

        @NotBlank(message = "El código postal es obligatorio")
        String codigoPostal,

        @NotNull(message = "La provincia es obligatoria")
        UUID provinciaId
) {
}