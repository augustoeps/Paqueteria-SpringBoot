package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.tarifa.CreateTarifaUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class CreateTarifaService implements CreateTarifaUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;
    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public CreateTarifaService(TarifaRepositoryPort tarifaRepositoryPort, ProvinciaRepositoryPort provinciaRepositoryPort){
        this.tarifaRepositoryPort=tarifaRepositoryPort;
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }
    @Override
    public Tarifa create(UUID provinciaOrigenId, UUID provinciaDestinoId, TarifaPrecioPorKilogramo precioPorKilogramo) {

        Optional<Provincia> provinciaOrigen = this.provinciaRepositoryPort.findById(provinciaOrigenId);
        Optional<Provincia> provinciaDestino = this.provinciaRepositoryPort.findById(provinciaDestinoId);

        if(provinciaOrigen.isEmpty() || provinciaDestino.isEmpty()){
            throw new RecursoNoEncontradoException(
                    "La provincia origen o destino no existe en la bd. Origen: " + provinciaOrigenId + ", Destino: " + provinciaDestinoId
            );        }
        if (tarifaRepositoryPort.findByOrigenAndDestino(provinciaOrigenId, provinciaDestinoId).isPresent()) {
            throw new RecursoNoEncontradoException("Ya existe una tarifa para esa ruta");
        }
        Tarifa tarifa =  new Tarifa(provinciaOrigenId,provinciaDestinoId,precioPorKilogramo);
        return this.tarifaRepositoryPort.save(tarifa);
    }
}
