package com.example.paqueteria.application.port.in.provincia;

import com.example.paqueteria.domain.entity.Provincia;

import java.util.List;

public interface FindAllProvinciaUseCase {

    List<Provincia> getAll();

}
