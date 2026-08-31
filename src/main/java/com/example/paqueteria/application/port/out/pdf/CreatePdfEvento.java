package com.example.paqueteria.application.port.out.pdf;

import com.example.paqueteria.domain.entity.Paquete;

public record CreatePdfEvento(String
                              remitente, String destinatario, double peso, double costo,
                              String fecha) {
}
