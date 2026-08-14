package com.example.paqueteria.application.service.oficina;

import com.example.paqueteria.application.port.in.oficina.FindAllOficinaUseCase;
import com.example.paqueteria.application.port.in.oficina.FindOficinaByIdUseCase;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.domain.entity.Oficina;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindOficinaService implements FindOficinaByIdUseCase, FindAllOficinaUseCase {

    private final OficinaRepositoryPort oficinaRepositoryPort;

    public FindOficinaService(OficinaRepositoryPort oficinaRepositoryPort){
        this.oficinaRepositoryPort = oficinaRepositoryPort;
    }
    @Override
    public List<Oficina> findAll() {
        return this.oficinaRepositoryPort.findAll();
    }

    @Override
    public Optional<Oficina> findById(UUID id) {
        return this.oficinaRepositoryPort.findById(id);
    }
}
