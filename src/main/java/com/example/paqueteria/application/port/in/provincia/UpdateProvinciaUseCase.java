package com.example.paqueteria.application.port.in.provincia;

import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;

import java.util.UUID;

public interface UpdateProvinciaUseCase {

    Provincia update(UUID id, ProvinciaNombre nombre);
}