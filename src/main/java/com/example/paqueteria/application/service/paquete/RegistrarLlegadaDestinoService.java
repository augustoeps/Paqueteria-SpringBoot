package com.example.paqueteria.application.service.paquete;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.paquete.RegistrarLlegadaDestinoUseCase;
import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Paquete;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrarLlegadaDestinoService implements RegistrarLlegadaDestinoUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;
    private final OficinaRepositoryPort oficinaRepositoryPort;
    private final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;

    public RegistrarLlegadaDestinoService(PaqueteRepositoryPort paqueteRepositoryPort, OficinaRepositoryPort oficinaRepositoryPort, HistorialEstadoRepositoryPort historialEstadoRepositoryPort){
        this.paqueteRepositoryPort = paqueteRepositoryPort;
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
    }

    @Override
    public Paquete registrarLlegadaDestino(UUID paqueteId, UUID oficinaId) {
        Optional<Oficina> oficina = this.oficinaRepositoryPort.findById(oficinaId);
        Optional<Paquete> paquete = this.paqueteRepositoryPort.findById(paqueteId);
        if(oficina.isEmpty() || paquete.isEmpty()){
            throw new RecursoNoEncontradoException("Oficina o paquete no encontrado");
        }
        HistorialEstado historialEstado = paquete.get().registrarLlegadaDestino(oficinaId);

        Paquete paqueteActulizado = this.paqueteRepositoryPort.save(paquete.get());

        this.historialEstadoRepositoryPort.save(historialEstado);

        return paqueteActulizado;
    }
}
