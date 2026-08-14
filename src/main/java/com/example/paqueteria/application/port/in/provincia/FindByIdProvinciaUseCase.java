package com.example.paqueteria.application.port.in.provincia;

import com.example.paqueteria.domain.entity.Provincia;

import java.util.Optional;
import java.util.UUID;

public interface FindByIdProvinciaUseCase {
    Optional<Provincia> findById(UUID id);

}
