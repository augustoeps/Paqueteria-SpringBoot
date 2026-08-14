package com.example.paqueteria.application.service.paquete;

import com.example.paqueteria.application.port.in.paquete.FindAllPaqueteUseCase;
import com.example.paqueteria.application.port.in.paquete.FindPaqueteByCodigoSeguimientoUseCase;
import com.example.paqueteria.application.port.in.paquete.FindPaqueteByIdUseCase;
import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class FindPaqueteService implements FindAllPaqueteUseCase, FindPaqueteByIdUseCase, FindPaqueteByCodigoSeguimientoUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;

    public FindPaqueteService(PaqueteRepositoryPort paqueteRepositoryPort){
        this.paqueteRepositoryPort = paqueteRepositoryPort;
    }

    @Override
    public List<Paquete> findAll() {
        return this.paqueteRepositoryPort.findAll();
    }

    @Override
    public Optional<Paquete> findByCodigoSeguimiento(PaqueteCodigoSeguimiento codigoSeguimiento) {
        return this.paqueteRepositoryPort.findByCodigoSeguimiento(codigoSeguimiento);
    }

    @Override
    public Optional<Paquete> FindById(UUID id) {
        return paqueteRepositoryPort.findById(id);
    }
}
