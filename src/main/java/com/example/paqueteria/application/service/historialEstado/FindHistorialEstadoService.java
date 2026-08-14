package com.example.paqueteria.application.service.historialEstado;

import com.example.paqueteria.application.port.in.historialEstado.FindHistorialByIdUseCase;
import com.example.paqueteria.application.port.in.historialEstado.FindHistorialByPaqueteIdUseCase;
import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.domain.entity.HistorialEstado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FindHistorialEstadoService implements FindHistorialByIdUseCase, FindHistorialByPaqueteIdUseCase {

    private final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;

    public FindHistorialEstadoService(HistorialEstadoRepositoryPort historialEstadoRepositoryPort) {
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
    }

    @Override
    public Optional<HistorialEstado> findById(UUID id) {
        return historialEstadoRepositoryPort.findById(id);
    }

    @Override
    public List<HistorialEstado> findByPaqueteId(UUID paqueteId) {
        return historialEstadoRepositoryPort.findByPaqueteId(paqueteId);
    }
}