package com.example.paqueteria.infrastructure.adapter.out.persistence.paquete;

import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaqueteRepositoryAdapter implements PaqueteRepositoryPort {

    private final PaqueteJpaRepository jpaRepository;
    private final PaqueteMapper mapper;

    public PaqueteRepositoryAdapter(PaqueteJpaRepository jpaRepository, PaqueteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Paquete save(Paquete paquete) {
        PaqueteJpaEntity entity = mapper.toJpaEntity(paquete);
        PaqueteJpaEntity guardado = jpaRepository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public List<Paquete> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Paquete> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Paquete> findByCodigoSeguimiento(PaqueteCodigoSeguimiento codigoSeguimiento) {
        return jpaRepository.findByCodigoSeguimiento(codigoSeguimiento.getCodigo())
                .map(mapper::toDomain);
    }
}