package com.example.paqueteria.infrastructure.adapter.out.persistence.HistorialEstado;

import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.domain.entity.HistorialEstado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HistorialEstadoRepositoryAdapter implements HistorialEstadoRepositoryPort {

    private final HistorialEstadoMapper mapper;
    private final HistorialEstadoJpaRepository jpaRepository;

    public HistorialEstadoRepositoryAdapter(HistorialEstadoMapper mapper, HistorialEstadoJpaRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public HistorialEstado save(HistorialEstado historialEstado) {
        HistorialEstadoJpaEntity entity = mapper.toJpaEntity(historialEstado);
        HistorialEstadoJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<HistorialEstado> findByPaqueteId(UUID paqueteId) {
        return jpaRepository.findByPaqueteId(paqueteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<HistorialEstado> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
}
