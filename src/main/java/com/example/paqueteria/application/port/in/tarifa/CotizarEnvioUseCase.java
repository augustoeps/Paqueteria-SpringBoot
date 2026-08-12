package com.example.paqueteria.application.port.in.tarifa;

import com.example.paqueteria.domain.valueobjects.PaquetePeso;

import java.math.BigDecimal;
import java.util.UUID;

public interface CotizarEnvioUseCase {
    BigDecimal cotizar(UUID provinciaOrigenId, UUID provinciaDestinoId, PaquetePeso peso);
}