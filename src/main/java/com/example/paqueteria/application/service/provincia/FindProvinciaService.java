package com.example.paqueteria.application.service.provincia;

import com.example.paqueteria.application.port.in.provincia.FindAllProvinciaUseCase;
import com.example.paqueteria.application.port.in.provincia.FindByIdProvinciaUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindProvinciaService  implements FindAllProvinciaUseCase, FindByIdProvinciaUseCase {

    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public FindProvinciaService(ProvinciaRepositoryPort provinciaRepositoryPort) {
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }



    @Override
    public List<Provincia> getAll() {
        return provinciaRepositoryPort.findAll();
    }

    @Override
    public Optional<Provincia> findById(UUID id) {
        return provinciaRepositoryPort.findById(id);

    }
}
