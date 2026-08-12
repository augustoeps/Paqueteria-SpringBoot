package com.example.paqueteria.application.port.in.paquete;

import com.example.paqueteria.domain.entity.Paquete;

import java.util.UUID;

public interface EntregarPaqueteUseCase {
    Paquete entregar(UUID paqueteId, UUID oficinaId);
}