package com.example.paqueteria.application.service.oficina;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.oficina.CreateOficinaUseCase;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateOficinaService implements CreateOficinaUseCase {

    private final OficinaRepositoryPort oficinaRepositoryPort;
    private final ProvinciaRepositoryPort provinciaRepositoryPort;
    public CreateOficinaService(OficinaRepositoryPort oficinaRepositoryPort, ProvinciaRepositoryPort provinciaRepositoryPort){
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public Oficina create(OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion oficinaDireccion, UUID provinciaId) {
        if (provinciaRepositoryPort.findById(provinciaId).isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró la provincia con id: " + provinciaId);
        }

        Oficina oficina = new Oficina(codigo, nombre, oficinaDireccion, provinciaId);
        return oficinaRepositoryPort.save(oficina);
    }
}
