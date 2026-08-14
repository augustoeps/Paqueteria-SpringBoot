package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.port.in.tarifa.UpdateTarifaUseCase;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;

import java.util.Optional;
import java.util.UUID;

public class UpdateTarifaService implements UpdateTarifaUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;


    public UpdateTarifaService(TarifaRepositoryPort tarifaRepositoryPort){
        this.tarifaRepositoryPort = tarifaRepositoryPort;

    }


    @Override
    public Tarifa update(UUID id, TarifaPrecioPorKilogramo precioPorKilogramo) {
        return null;
    }
}
