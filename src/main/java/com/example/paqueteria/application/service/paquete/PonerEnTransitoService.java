package com.example.paqueteria.application.service.paquete;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.paquete.PonerEnTransitoUseCase;
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
public class PonerEnTransitoService implements PonerEnTransitoUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;
    private final OficinaRepositoryPort oficinaRepositoryPort;
    private  final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;
    public PonerEnTransitoService(PaqueteRepositoryPort paqueteRepositoryPort,OficinaRepositoryPort oficinaRepositoryPort, HistorialEstadoRepositoryPort historialEstadoRepositoryPort){
        this.paqueteRepositoryPort = paqueteRepositoryPort;
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
    }

    @Override
    public Paquete ponerEnTransito(UUID paqueteId, UUID oficinaId) {

        Optional<Paquete> paquete = this.paqueteRepositoryPort.findById(paqueteId);
        Optional<Oficina> oficina = this.oficinaRepositoryPort.findById(oficinaId);

        if(oficina.isEmpty() || paquete.isEmpty()){
            throw new RecursoNoEncontradoException("Paquete u oficina no encontrado");
        }

        HistorialEstado historialEstado = paquete.get().ponerEnTransito(oficinaId);

        Paquete paqueteActualizado = this.paqueteRepositoryPort.save(paquete.get()); // ← faltaba esto
        this.historialEstadoRepositoryPort.save(historialEstado);

        return paqueteActualizado;
    }
}
