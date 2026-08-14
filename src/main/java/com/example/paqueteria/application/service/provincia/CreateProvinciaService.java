package com.example.paqueteria.application.service.provincia;


import com.example.paqueteria.application.port.in.provincia.CreateProvinciaUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateProvinciaService implements CreateProvinciaUseCase {

    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public CreateProvinciaService(ProvinciaRepositoryPort provinciaRepositoryPort){
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public Provincia create(ProvinciaNombre nombre) {
        Provincia provincia = new Provincia(nombre);
        return this.provinciaRepositoryPort.save(provincia);
    }
}
