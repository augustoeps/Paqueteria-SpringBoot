package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;

import java.util.Optional;
import java.util.UUID;

public interface FindTarifaByOrigenAndDestinoUseCase {

    Optional<Tarifa> findByOrigenAndDestino(
            UUID provinciaOrigenId,
            UUID provinciaDestinoId
    );
}