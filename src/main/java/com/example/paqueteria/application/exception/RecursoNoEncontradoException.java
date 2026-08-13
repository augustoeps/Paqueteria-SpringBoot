package com.example.paqueteria.application.exception;

import java.util.UUID;

public class ProvinciaNoEncontradaException extends RuntimeException {

    public ProvinciaNoEncontradaException(UUID provinciaId) {
        super("No se encontró la provincia con id: " + provinciaId);
    }
}

