package com.example.paqueteria.infrastructure.adapter.out.persistence.paquete;

import com.example.paqueteria.infrastructure.adapter.out.persistence.provincia.ProvinciaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaqueteJpaRepository extends JpaRepository<PaqueteJpaEntity, UUID> {
}