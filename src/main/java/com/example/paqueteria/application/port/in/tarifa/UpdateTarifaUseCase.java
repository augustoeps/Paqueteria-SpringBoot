package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;

import java.util.UUID;

public interface UpdateTarifaUseCase {
    Tarifa update(UUID id, TarifaPrecioPorKilogramo precioPorKilogramo);
}

