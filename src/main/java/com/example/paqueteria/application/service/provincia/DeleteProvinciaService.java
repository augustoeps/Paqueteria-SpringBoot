package com.example.paqueteria.application.service.provincia;

import com.example.paqueteria.application.port.in.provincia.DeleteProvinciaUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteProvinciaService implements DeleteProvinciaUseCase {

    private final  ProvinciaRepositoryPort provinciaRepositoryPort;

    public DeleteProvinciaService(ProvinciaRepositoryPort provinciaRepositoryPort){
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public boolean delete(UUID id) {
        Optional<Provincia> provincia = provinciaRepositoryPort.findById(id);

        if (provincia.isEmpty()) {
            return false;
        }

        return provinciaRepositoryPort.deleteById(id);
    }
}
