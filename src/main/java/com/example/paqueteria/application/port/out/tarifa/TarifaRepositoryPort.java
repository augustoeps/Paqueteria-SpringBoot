package com.example.paqueteria.application.port.out.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarifaRepositoryPort {

    Tarifa save(Tarifa tarifa);
    Tarifa update(Tarifa tarifa);
    Optional<Tarifa> findById(UUID id);
    List<Tarifa> findAll();
    Optional<Tarifa> findByOrigenAndDestino(UUID provinciaOrigenId, UUID provinciaDestinoId);
    boolean deleteById(UUID id);
}
