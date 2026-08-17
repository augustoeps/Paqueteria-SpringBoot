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
                oficina.getDireccion().toString(), // Esto guarda: "calle, numero, ciudad, cp"
                oficina.getProvinciaId()
        );
    }

    public Oficina toDomain(OficinaJpaEntity oficina) {
        // Dividimos el string guardado por la coma
        String[] partes = oficina.getDireccion().split(", ");

        return new Oficina(
                oficina.getId(),
                new OficinaCodigo(oficina.getCodigo()),
                new OficinaNombre(oficina.getNombre()),
                // Asumiendo el orden exacto del toString(): calle, numero, ciudad, cp
                new OficinaDireccion(partes[3], partes[2], partes[0], partes[1]),
                oficina.getProvinciaId()
        );
    }
}