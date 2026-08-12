package com.example.paqueteria.application.port.out.provincia;

import com.example.paqueteria.domain.entity.Provincia;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvinciaRepositoryPort {
    Provincia save(Provincia provincia);
    Optional<Provincia> findById(UUID id);
    List<Provincia> findAll();
    boolean deleteById(UUID id);
}