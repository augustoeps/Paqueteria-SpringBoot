package com.example.paqueteria.application.port.out.estadisticas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaqueteEstadisticaEvento(UUID paqueteId,
                                       String tipoEvento,// como String, ya que el enum TipoEvento del microservicio no existe en el monolito
                                       BigDecimal tarifaAplicada,
                                       UUID oficinaId,
                                       UUID provinciaOrigenId,
                                       UUID provinciaDestinoId,
                                       LocalDateTime fecha) {
}
