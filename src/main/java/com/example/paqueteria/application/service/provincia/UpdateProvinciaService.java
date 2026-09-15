package com.example.paqueteria.application.service.provincia;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.provincia.UpdateProvinciaUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateProvinciaService implements UpdateProvinciaUseCase {

    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public UpdateProvinciaService(ProvinciaRepositoryPort provinciaRepositoryPort) {
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public Provincia update(UUID id, ProvinciaNombre nombre) {

        Optional<Provincia> provinciaExistente = this.provinciaRepositoryPort.findById(id);

        if (provinciaExistente.isEmpty()) {
            throw new RecursoNoEncontradoException("Provincia no encontrada con id: " + id);
        }

        Provincia provinciaActualizada = new Provincia(id, nombre);

        return this.provinciaRepositoryPort.update(provinciaActualizada);
    }
}