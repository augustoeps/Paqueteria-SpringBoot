package com.example.paqueteria.application.port.out.pdf;

import com.example.paqueteria.domain.entity.Paquete;

import java.math.BigDecimal;

public record CreatePdfEvento(String
                              remitente, String destinatario, BigDecimal peso, BigDecimal costo,
                              String fecha) {
}
