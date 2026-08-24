package com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record CambiarRolRequest(
        @NotBlank(message = "El rol es obligatorio")
        String rol
) {
}