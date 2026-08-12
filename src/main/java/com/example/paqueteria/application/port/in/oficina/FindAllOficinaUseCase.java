package com.example.paqueteria.application.port.in.oficina;

import com.example.paqueteria.domain.entity.Oficina;

import java.util.List;

public interface FindAllOficinaUseCase {

    List<Oficina> findAll();
}
