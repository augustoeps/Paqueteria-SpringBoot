package com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto;

import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.enums.EstadoPaquete;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaqueteResponse(
        UUID id,
        String codigoSeguimiento,
        BigDecimal peso,
        EstadoPaquete estadoPaquete,
        UUID oficinaOrigenId,
        UUID oficinaDestinoId,
        DatosContactoResponse remitente,
        DatosContactoResponse destinatario,
        BigDecimal tarifaAplicada,
        LocalDateTime fechaCreacion
) {
    public static PaqueteResponse desde(Paquete paquete) {
        return new PaqueteResponse(
                paquete.getId(),
                paquete.getCodigoSeguimiento().getCodigo(),
                paquete.getPeso().getKilogramos(),
                paquete.getEstadoPaquete(),
                paquete.getOficinaOrigenId(),
                paquete.getOficinaDestinoId(),
                DatosContactoResponse.desde(paquete.getRemitente()),
                DatosContactoResponse.desde(paquete.getDestinatario()),
                paquete.getTarifaAplicada().getMonto(),
                paquete.getFechaCreacion()
        );
    }
}