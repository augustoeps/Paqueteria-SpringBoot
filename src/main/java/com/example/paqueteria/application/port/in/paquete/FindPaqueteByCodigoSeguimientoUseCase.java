package com.example.paqueteria.application.port.in.paquete;

import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;

import java.util.Optional;

public interface FindPaqueteByCodigoSeguimientoUseCase {
    Optional<Paquete> findByCodigoSeguimiento(PaqueteCodigoSeguimiento codigoSeguimiento);
}