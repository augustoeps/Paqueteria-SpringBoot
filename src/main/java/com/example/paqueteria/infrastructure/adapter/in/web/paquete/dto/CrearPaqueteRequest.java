package com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CrearPaqueteRequest(
        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor que cero")
        BigDecimal peso,

        @NotNull(message = "La oficina de origen es obligatoria")
        UUID oficinaOrigenId,

        @NotNull(message = "La oficina de destino es obligatoria")
        UUID oficinaDestinoId,

        @Valid
        @NotNull(message = "Los datos del remitente son obligatorios")
        DatosContactoRequest remitente,

        @Valid
        @NotNull(message = "Los datos del destinatario son obligatorios")
        DatosContactoRequest destinatario
) {
}