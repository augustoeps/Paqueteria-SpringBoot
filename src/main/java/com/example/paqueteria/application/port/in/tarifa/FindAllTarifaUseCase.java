package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.entity.Tarifa;

import java.util.List;

public interface FindAllTarifaUseCase {
    List<Tarifa> findAll();
}