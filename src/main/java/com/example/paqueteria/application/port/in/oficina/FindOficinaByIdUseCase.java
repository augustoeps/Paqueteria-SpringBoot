package com.example.paqueteria.application.port.in.oficina;

import com.example.paqueteria.domain.entity.Oficina;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindOficinaByIdUseCase {

    Optional<Oficina> findById(UUID id);
}
