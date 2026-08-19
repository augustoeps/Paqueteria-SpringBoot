package com.example.paqueteria.infrastructure.adapter.out.persistence.oficina;

import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.infrastructure.adapter.out.persistence.provincia.ProvinciaJpaRepository;
import com.example.paqueteria.infrastructure.adapter.out.persistence.provincia.ProvinciaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public class OficinaRepositoryAdapter implements OficinaRepositoryPort {

    private final OficinaJpaRepository jpaRepository;
    private final OficinaMapper mapper;

    public OficinaRepositoryAdapter(OficinaJpaRepository jpaRepository, OficinaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }


    @Override
    public Oficina save(Oficina oficina) {
        OficinaJpaEntity oficinaJPA = mapper.toJpaEntity(oficina);
        OficinaJpaEntity oficinaJPA2 = this.jpaRepository.save(oficinaJPA);
        return mapper.toDomain(oficinaJPA2);

    }

    @Override
    public Optional<Oficina> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);

    }

    @Override
    public List<Oficina> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean deleteById(UUID id) {
        if(!this.jpaRepository.existsById(id)){
            return false;
        }
        this.jpaRepository.deleteById(id);
        return  true;
    }
}
