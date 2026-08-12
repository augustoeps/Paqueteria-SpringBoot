package com.example.paqueteria.application.port.in.paquete;

import com.example.paqueteria.domain.entity.Paquete;

import java.util.List;

public interface FindAllPaqueteUseCase {
    List<Paquete> findAll();
}