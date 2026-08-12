package com.example.paqueteria.application.port.in.provincia;

import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;

public interface CreateProvinciaUseCase {

    Provincia create(ProvinciaNombre nombre);
}
