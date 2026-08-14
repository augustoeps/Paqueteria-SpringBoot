package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.tarifa.FindAllTarifaUseCase;
import com.example.paqueteria.application.port.in.tarifa.FindByIdTarifaUseCase;
import com.example.paqueteria.application.port.in.tarifa.FindTarifaByOrigenAndDestinoUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.entity.Tarifa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindTarifaService implements FindByIdTarifaUseCase, FindAllTarifaUseCase, FindTarifaByOrigenAndDestinoUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;
    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public FindTarifaService(TarifaRepositoryPort tarifaRepositoryPort, ProvinciaRepositoryPort provinciaRepositoryPort){
        this.tarifaRepositoryPort = tarifaRepositoryPort;
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public Optional<Tarifa> findById(UUID id) {
        return this.tarifaRepositoryPort.findById(id);
    }

    @Override
    public List<Tarifa> findAll() {
        return this.tarifaRepositoryPort.findAll();
    }

    @Override
    public Optional<Tarifa> findByOrigenAndDestino(UUID provinciaOrigenId, UUID provinciaDestinoId) {
        Optional<Provincia> provinciaOrigen = this.provinciaRepositoryPort.findById(provinciaOrigenId);
        Optional<Provincia> provinciaDestino = this.provinciaRepositoryPort.findById(provinciaDestinoId);

        if(provinciaDestino.isEmpty() || provinciaOrigen.isEmpty()){
            throw new RecursoNoEncontradoException("La provincia origen o destino no existe");
        }

        return this.tarifaRepositoryPort.findByOrigenAndDestino(provinciaOrigenId,provinciaDestinoId);
    }
}
