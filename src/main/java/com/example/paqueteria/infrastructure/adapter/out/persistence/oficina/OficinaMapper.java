package com.example.paqueteria.infrastructure.adapter.out.persistence.oficina;

import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;
import org.springframework.stereotype.Component;

@Component
public class OficinaMapper {

    public OficinaJpaEntity toJpaEntity(Oficina oficina) {
        return new OficinaJpaEntity(
                oficina.getId(),
                oficina.getCodigo().getCodigo(),
                oficina.getNombre().getNombre(),
                oficina.getDireccion().getCalle(),
                oficina.getDireccion().getNumero(),
                oficina.getDireccion().getCiudad(),
                oficina.getDireccion().getCodigoPostal(),
                oficina.getProvinciaId()
        );
    }

    public Oficina toDomain(OficinaJpaEntity entity) {
        return new Oficina(
                entity.getId(),
                new OficinaCodigo(entity.getCodigo()),
                new OficinaNombre(entity.getNombre()),
                new OficinaDireccion(
                        entity.getCodigoPostal(),
                        entity.getCiudad(),
                        entity.getCalle(),
                        entity.getNumero()
                ),
                entity.getProvinciaId()
        );
    }
}