package com.example.paqueteria.infrastructure.adapter.out.persistence.HistorialEstado;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistorialEstadoJpaRepository extends JpaRepository<HistorialEstadoJpaEntity, UUID> {

    List<HistorialEstadoJpaEntity> findByPaqueteId(UUID paqueteId);
}
