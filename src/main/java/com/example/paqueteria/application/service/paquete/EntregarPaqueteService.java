package com.example.paqueteria.application.service.paquete;


import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.paquete.EntregarPaqueteUseCase;
import com.example.paqueteria.application.port.out.estadisticas.PaqueteEstadisticaEvento;
import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.application.port.out.notifications.EventPublisherPort;
import com.example.paqueteria.application.port.out.notifications.NotificacionEmailEvento;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Paquete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntregarPaqueteService implements EntregarPaqueteUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;
    private final OficinaRepositoryPort oficinaRepositoryPort;
    private final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public EntregarPaqueteService(PaqueteRepositoryPort paqueteRepositoryPort, OficinaRepositoryPort oficinaRepositoryPort, HistorialEstadoRepositoryPort historialEstadoRepositoryPort, EventPublisherPort eventPublisherPort){
        this.paqueteRepositoryPort = paqueteRepositoryPort;
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional
    public Paquete entregar(UUID paqueteId, UUID oficinaId) {
        Optional<Oficina> oficinaTransicion = this.oficinaRepositoryPort.findById(oficinaId);
        Optional<Paquete> paquete = this.paqueteRepositoryPort.findById(paqueteId);
        if (oficinaTransicion.isEmpty() || paquete.isEmpty()) {
            throw new RecursoNoEncontradoException("Oficina o paquete no encontrado");
        }
        HistorialEstado historialEstado = paquete.get().entregar(oficinaId);

        Paquete paqueteActualizado = this.paqueteRepositoryPort.save(paquete.get());
        this.historialEstadoRepositoryPort.save(historialEstado);

        String cuerpo = "Su paquete con código " + paqueteActualizado.getCodigoSeguimiento().getCodigo()
                + " fue entregado el día " + new Date() + " en la oficina " + oficinaTransicion.get().getDireccion().toString();

        NotificacionEmailEvento evento = new NotificacionEmailEvento(
                paqueteActualizado.getDestinatario().getEmail(),
                "Paquete Entregado con éxito",
                cuerpo
        );
        this.eventPublisherPort.publicarEmail(evento);

        Optional<Oficina> oficinaOrigen = this.oficinaRepositoryPort.findById(paqueteActualizado.getOficinaOrigenId());
        Optional<Oficina> oficinaDestino = this.oficinaRepositoryPort.findById(paqueteActualizado.getOficinaDestinoId());

        if (oficinaOrigen.isEmpty() || oficinaDestino.isEmpty()) {
            throw new RecursoNoEncontradoException("No se pudo resolver la oficina de origen/destino del paquete");
        }

        PaqueteEstadisticaEvento eventoEstadistica = new PaqueteEstadisticaEvento(
                paqueteActualizado.getId(),
                paqueteActualizado.getEstadoPaquete().name(),
                paqueteActualizado.getTarifaAplicada().getMonto(),
                oficinaId,
                oficinaOrigen.get().getProvinciaId(),
                oficinaDestino.get().getProvinciaId(),
                paqueteActualizado.getFechaCreacion()
        );

        this.eventPublisherPort.publicarEstadistica(eventoEstadistica);

        return paqueteActualizado;
    }
}
