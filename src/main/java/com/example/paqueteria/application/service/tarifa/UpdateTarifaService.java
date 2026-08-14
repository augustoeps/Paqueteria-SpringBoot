package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.tarifa.UpdateTarifaUseCase;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateTarifaService implements UpdateTarifaUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;

    public UpdateTarifaService(TarifaRepositoryPort tarifaRepositoryPort) {
        this.tarifaRepositoryPort = tarifaRepositoryPort;
    }

    @Override
    public Tarifa update(UUID id, TarifaPrecioPorKilogramo precioPorKilogramo) {

        Optional<Tarifa> tarifaExistente = this.tarifaRepositoryPort.findById(id);

        if (tarifaExistente.isEmpty()) {
            throw new RecursoNoEncontradoException("Recurso tarifa no encontrado");
        }

        Tarifa tarifaActualizada = new Tarifa(
                id,
                tarifaExistente.get().getProvinciaOrigenId(),
                tarifaExistente.get().getProvinciaDestinoId(),
                precioPorKilogramo
        );

        return this.tarifaRepositoryPort.update(tarifaActualizada);
    }
}