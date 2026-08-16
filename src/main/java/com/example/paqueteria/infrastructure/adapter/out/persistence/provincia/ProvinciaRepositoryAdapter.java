package com.example.paqueteria.infrastructure.adapter.out.persistence.provincia;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProvinciaRepositoryAdapter implements ProvinciaRepositoryPort {

    private final ProvinciaJpaRepository jpaRepository;
    private final ProvinciaMapper mapper;

    public ProvinciaRepositoryAdapter(ProvinciaJpaRepository jpaRepository, ProvinciaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Provincia save(Provincia provincia) {
        ProvinciaJpaEntity entity = mapper.toJpaEntity(provincia);
        ProvinciaJpaEntity guardado = jpaRepository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<Provincia> findById(UUID id) {
        // Si jpaRepository.findById encuentra la entidad, .map() ejecuta mapper.toDomain()
        // y envuelve el resultado en un Optional.of(...).
        // Si no encuentra nada (Optional vacío), .map() NO ejecuta el mapper,
        // simplemente retorna Optional.empty() directamente.

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Provincia> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();

        //Por dentro se esta ejecutando esto
        //List<ProvinciaJpaEntity> entidades = jpaRepository.findAll();
        //List<Provincia> provincias = new ArrayList<>();

        //for (ProvinciaJpaEntity entity : entidades) {
          //  Provincia provincia = mapper.toDomain(entity);
            //provincias.add(provincia);
        //}

        //return provincias;

    }

    @Override
    public boolean deleteById(UUID id) {
        boolean valor =  jpaRepository.existsById(id);
        if(!valor){
            return false;
        }
        jpaRepository.deleteById(id);
        return true;

    }
}
