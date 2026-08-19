package com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto;

import jakarta.validation.constraints.NotBlank;

public record DireccionContactoRequest(
        @NotBlank(message = "La calle es obligatoria")
        String calle,

        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad,

        @NotBlank(message = "La provincia es obligatoria")
        String provincia,

        @NotBlank(message = "El código postal es obligatorio")
        String codigoPostal
) {
}