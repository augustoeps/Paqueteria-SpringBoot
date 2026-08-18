package com.example.paqueteria.infrastructure.adapter.out.persistence.tarifa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TarifaJpaRepository extends JpaRepository<TarifaJpaEntity, UUID> {

    Optional<TarifaJpaEntity> findByProvinciaOrigenIdAndProvinciaDestinoId(
            UUID provinciaOrigenId,
            UUID provinciaDestinoId
    );

}
