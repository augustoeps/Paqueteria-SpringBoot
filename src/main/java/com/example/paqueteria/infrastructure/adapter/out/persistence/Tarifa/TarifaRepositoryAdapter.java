package com.example.paqueteria.infrastructure.adapter.out.persistence.Tarifa;

import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Tarifa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TarifaRepositoryAdapter implements TarifaRepositoryPort {

    private final TarifaMapper mapper;

    private final TarifaJpaRepository tarifaJpaRepository;

    public TarifaRepositoryAdapter(TarifaMapper mapper, TarifaJpaRepository tarifaJpaRepository) {
        this.mapper = mapper;
        this.tarifaJpaRepository = tarifaJpaRepository;
    }

    @Override
    public Tarifa save(Tarifa tarifa) {
        TarifaJpaEntity tarifaJpa = mapper.toJpaEntity(tarifa);
        TarifaJpaEntity tarifaDevuelta = tarifaJpaRepository.save(tarifaJpa);
        return this.mapper.toDomain(tarifaDevuelta);
    }

    @Override
    public Tarifa update(Tarifa tarifa) {
        TarifaJpaEntity tarifaJpa = mapper.toJpaEntity(tarifa);
        TarifaJpaEntity tarifaDevuelta = tarifaJpaRepository.save(tarifaJpa);
        return this.mapper.toDomain(tarifaDevuelta);
    }

    @Override
    public Optional<Tarifa> findById(UUID id) {
        return tarifaJpaRepository.findById(id).map(mapper::toDomain);

    }

    @Override
    public List<Tarifa> findAll() {
        return tarifaJpaRepository.findAll().stream().map(mapper::toDomain).toList();

    }

    @Override
    public Optional<Tarifa> findByOrigenAndDestino(UUID provinciaOrigenId, UUID provinciaDestinoId) {
        return tarifaJpaRepository.findByProvinciaOrigenIdAndProvinciaDestinoId(provinciaOrigenId, provinciaDestinoId)
                .map(mapper::toDomain);
    }

    @Override
    public boolean deleteById(UUID id) {
        if(!this.tarifaJpaRepository.existsById(id)){
            return false;
        }
        this.tarifaJpaRepository.deleteById(id);
        return  true;
    }
}
