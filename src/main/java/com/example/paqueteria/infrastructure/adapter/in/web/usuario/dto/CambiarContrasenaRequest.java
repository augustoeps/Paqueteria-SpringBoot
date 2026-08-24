package com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record CambiarContrasenaRequest(
        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena
) {
}
