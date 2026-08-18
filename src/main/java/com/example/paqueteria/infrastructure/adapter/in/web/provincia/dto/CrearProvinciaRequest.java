package com.example.paqueteria.infrastructure.adapter.in.web.provincia.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearProvinciaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
}