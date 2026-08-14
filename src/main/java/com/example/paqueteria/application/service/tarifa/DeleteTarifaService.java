package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.tarifa.DeleteTarifaUseCase;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Tarifa;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class DeleteTarifaService implements DeleteTarifaUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;

    public DeleteTarifaService(TarifaRepositoryPort tarifaRepositoryPort){
        this.tarifaRepositoryPort = tarifaRepositoryPort;
    }

    @Override
    public boolean delete(UUID id) {

        Optional<Tarifa> tarifa =  this.tarifaRepositoryPort.findById(id);
        if(tarifa.isEmpty()){
            throw new RecursoNoEncontradoException("La tarifa no se pudo encontrar "+ id);
        }
        return this.tarifaRepositoryPort.deleteById(id);

    }
}
