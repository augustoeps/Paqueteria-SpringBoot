package com.example.paqueteria.application.service.oficina;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.oficina.DeleteOficinaUseCase;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.domain.entity.Oficina;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteOficinaService implements DeleteOficinaUseCase {

    private final OficinaRepositoryPort oficinaRepositoryPort;

    public DeleteOficinaService(OficinaRepositoryPort oficinaRepositoryPort) {
        this.oficinaRepositoryPort = oficinaRepositoryPort;
    }

    @Override
    public boolean delete(UUID id) {
        Optional<Oficina> oficina = this.oficinaRepositoryPort.findById(id);
        if(oficina.isEmpty()){
            throw new RecursoNoEncontradoException("El id de oficina no existe: " + id);
        }
        return this.oficinaRepositoryPort.deleteById(id);
    }
}
