package com.example.paqueteria.application.port.out.oficina;


import com.example.paqueteria.domain.entity.Oficina;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface OficinaRepositoryPort {
    Oficina save(Oficina oficina);
    Optional<Oficina> findById(UUID id);
    List<Oficina> findAll();
    boolean deleteById(UUID id);
}