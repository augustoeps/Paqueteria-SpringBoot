package com.example.paqueteria.infrastructure.adapter.out.persistence.provincia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvinciaJpaRepository extends JpaRepository<ProvinciaJpaEntity, UUID> {
}