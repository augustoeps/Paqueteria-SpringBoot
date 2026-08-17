package com.example.paqueteria.infrastructure.adapter.out.persistence.oficina;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OficinaJpaRepository extends JpaRepository<OficinaJpaEntity, UUID> {
}
