package com.example.paqueteria.application.service.paquete;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.paquete.RegistrarLlegadaDestinoUseCase;
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
public class RegistrarLlegadaDestinoService implements RegistrarLlegadaDestinoUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;
    private final OficinaRepositoryPort oficinaRepositoryPort;
    private final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public RegistrarLlegadaDestinoService(PaqueteRepositoryPort paqueteRepositoryPort, OficinaRepositoryPort oficinaRepositoryPort, HistorialEstadoRepositoryPort historialEstadoRepositoryPort, EventPublisherPort eventPublisherPort){
        this.paqueteRepositoryPort = paqueteRepositoryPort;
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional
    public Paquete registrarLlegadaDestino(UUID paqueteId, UUID oficinaId) {
        Optional<Oficina> oficina = this.oficinaRepositoryPort.findById(oficinaId);
        Optional<Paquete> paquete = this.paqueteRepositoryPort.findById(paqueteId);
        if(oficina.isEmpty() || paquete.isEmpty()){
            throw new RecursoNoEncontradoException("Oficina o paquete no encontrado");
        }
        HistorialEstado historialEstado = paquete.get().registrarLlegadaDestino(oficinaId);

        Paquete paqueteActualizado = this.paqueteRepositoryPort.save(paquete.get());

        this.historialEstadoRepositoryPort.save(historialEstado);

        String cuerpo = "Su paquete con código " + paqueteActualizado.getCodigoSeguimiento().getCodigo()
                + " ya se puede retitar en la oficina " + oficina.get().getDireccion().toString();

        NotificacionEmailEvento evento = new NotificacionEmailEvento(
                paqueteActualizado.getDestinatario().getEmail(),
                "Paquete Listo para Retirar",
                cuerpo
        );
        this.eventPublisherPort.publicarEmail(evento);

        return paqueteActualizado;
    }
}
