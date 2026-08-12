package com.example.paqueteria.application.port.in.paquete;

import com.example.paqueteria.domain.entity.Paquete;

import java.util.Optional;
import java.util.UUID;

public interface FindPaqueteByIdUseCase {


    Optional<Paquete> FindByIdUse(UUID id);
}
