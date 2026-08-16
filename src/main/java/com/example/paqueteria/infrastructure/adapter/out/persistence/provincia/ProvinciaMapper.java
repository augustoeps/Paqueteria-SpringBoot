package com.example.paqueteria.infrastructure.adapter.out.persistence.provincia;



import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;
import org.springframework.stereotype.Component;

@Component

public class ProvinciaMapper {

    public ProvinciaJpaEntity toJpaEntity(Provincia provincia) {
        return new ProvinciaJpaEntity(
                provincia.getId(),
                provincia.getNombre().getNombre() // "desenvuelve" el VO a String
        );
    }
    public Provincia toDomain(ProvinciaJpaEntity entity) {
        return new Provincia(
                entity.getId(),
                new ProvinciaNombre(entity.getNombre()) // "envuelve" el String de vuelta en el VO
        );
    }
}
