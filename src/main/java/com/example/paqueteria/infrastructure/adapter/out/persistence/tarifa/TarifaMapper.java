package com.example.paqueteria.infrastructure.adapter.out.persistence.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import org.springframework.stereotype.Component;

@Component
public class TarifaMapper {

    public TarifaJpaEntity toJpaEntity(Tarifa tarifa) {
        return new TarifaJpaEntity(
                tarifa.getId(),
                tarifa.getProvinciaOrigenId(),
                tarifa.getProvinciaDestinoId(),
                tarifa.getPrecioPorKilogramo().getPrecioPorKilogramo()
        );
    }

    public Tarifa toDomain(TarifaJpaEntity tarifa) {
        return new Tarifa(
                tarifa.getId(),
                tarifa.getProvinciaOrigenId(),
                tarifa.getProvinciaDestinoId(),
                new TarifaPrecioPorKilogramo(tarifa.getPrecioPorKilogramo())
        );
    }
}